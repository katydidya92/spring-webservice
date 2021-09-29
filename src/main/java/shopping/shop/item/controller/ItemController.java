package shopping.shop.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import shopping.shop.item.domain.Item;
import shopping.shop.item.domain.ItemDto;
import shopping.shop.item.repository.ItemRepository;
import shopping.shop.item.service.ItemRepositoryImpl;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;
import shopping.shop.upload.domian.UploadFile;
import shopping.shop.upload.file.FileStore;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemRepositoryImpl itemService;
    private final FileStore fileStore;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemDto());
        return "items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute("item") ItemDto form,
                          BindingResult bindingResult,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                          RedirectAttributes attributes) throws IOException {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/addForm";
        }

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        Item item = Item.builder()
                .itemName(form.getItemName())
                .price(form.getPrice())
                .quantity(form.getQuantity())
                .userId(member.getUserId())
                .attachFile(attachFile)
                .build();

        Item savedItem = itemRepository.save(item);
        attributes.addAttribute("Id", savedItem.getItemId());
        attributes.addAttribute("status", true);
        return "redirect:/items/{Id}";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        Item item = itemRepository.getById(itemId);
        model.addAttribute("member", member);
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping("/{itemId}/edit")
    public String openUpdateItem(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.getById(itemId);

        ItemDto itemDto = new ItemDto();
        itemDto.setItemId(item.getItemId());
        itemDto.setItemName(item.getItemName());
        itemDto.setPrice(item.getPrice());
        itemDto.setQuantity(item.getQuantity());

        model.addAttribute("form", itemDto);
        return "items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemDto form, BindingResult bindingResult) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/editForm";
        }

        itemService.updateItem(itemId, form);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/download/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {

        Item item = itemRepository.getById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" +
                encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
