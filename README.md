# AES-256 Encryption and Decryption
## 암호화 정책
Mode : ECB  
Padding : NoPadding
## 설명
NoPadding을 선택한 경우 암호화 전 평문 텍스트를 16바이트의 배수로 만들어주어야 한다. 해당 애플리케이션은 Padding 값을 아스키 코드 값인
'\0'(null)으로 사용하였다. 참고로 전화 번호는 16자리를 넘지 않으므로, Padding 값을 추가해주는 메소드에 정수 16을 하드코딩 하였다.
## 참고 자료
Algorithm List : https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html
