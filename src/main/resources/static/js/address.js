function findPostCode() {
    new daum.Postcode({
        oncomplete : function(data) {
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', '
                        + data.buildingName : data.buildingName);
            }
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById('roadAddr').value = roadAddr;
            document.getElementById('addrDetail').focus();

            if (roadAddr !== '') {
                document.getElementById("adEtc").value = extraRoadAddr;
            } else {
                document.getElementById("adEtc").value = '';
            }
        }
    }).open();
}