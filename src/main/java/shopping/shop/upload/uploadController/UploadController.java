package shopping.shop.upload.uploadController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shopping.shop.upload.file.FileStore;

import java.net.MalformedURLException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UploadController {

    private final FileStore fileStore;

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(filename));
        log.info("resource={}", resource);
        return resource;
    }
}
