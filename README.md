# Gözlerini Benimle Paylaş
Görme engelli bireyler için en büyük sorunlardan birisi teknolojileri nasıl kullanabilecekleri ya da gelişen teknolojinin bu kişilere ne gibi yenilikler sunduğudur. “Gözlerini Benimle Paylaş”, görme engelli bireylerin hayatını kolaylaştırmayı amaçlayan bir Android uygulamadır. Uygulamanın aktif bir toplu yaşam alanında kullanılabileceği düşünülürse görme engelli kullanıcılar için büyük bir kolaylık sağlanacaktır. 
Uygulamada, görme engelli bireyler görmekte zorlandıkları veya göremedikleri nesneler için uygulama üzerinden video çekerek yardım talebinde bulunabilirler. Bu yardım isteği ilk mevcut gönüllü olan bireye ulaştırılarak görme engelli bireyin sorunu üzerine, gönüllü kişinin ses mesajı göndererek yardım etmesi ile sağlanacaktır. Böylece onların yaşamını daha bağımsız hale getirmek amaçlanır. Bireyler günün her saatinde her yerden yardım isteyebilirler.  
Android uygulaması Java dilinde yazılmış olup, uygulamada tüm kullanıcı verileri , çekilen video ve ses kayıtları Google Firebase veri tabanı sistemine kayıt edilmektedir. Veritabanı işlemleri Firebase üzerindeki “Realtime Database” , resim ve video işlemleri ise “Storage” ile gerçekleştirilecektir.   

## Uygulama Tanıtımı  
Uygulamada 2 tip kullanıcı vardır.

- Gönüllü Birey
- Görme Engelli Birey

Uygulama anasayfasında “Görme Engelliyim” ve “Gönüllüyüm” şeklinde girişler bulunmaktadır. Kişiler seçtikleri kullanıcı tipine göre “Kayıt Ol” ve “Oturum Aç” sayfalarına yönlendirilirler. Sisteme kayıt olan bireylerin verileri Firebase Realtime Database’ de “GonulluKullanicilar” ve “GormeEngelliKullanicilar” tablolarına kayıt edilir. Kişiler oturum açmak istediğinde gerekli kontroller sağlanarak kendi anasayfalarına yönlendirilirler.

Görme engelli bireyler yardıma ihtiyacı olduğunda uygulama üzerinden video çekerek yardım talebinde bulunurlar. Çekilen bu videolar Fireabase Storage‘ da “GormeEngelliKullaniciVideolari” klasöründe tutulur. Video sisteme yüklendiğinde sistemdeki tüm gönüllü kullanıcılara bildirim gönderilir. 

Gönüllü bireyler anasayfasında  sistemde kayıtlı olan tüm videoları görüntüleyebilir. O an videoya cevap verebilecek olan gönüllü, ses kaydederek görme engelli kişinin problemine yardımcı olur. Ses kaydetme aşamasında kayıtlar, Firebase Storage’da “Audio” klasörü altında tutulur. Bu kayıt görme engelli kişiye sunularak iletişim sağlanmış ve problem çözülmüş olur.

Gönüllü bireyler, Ayarlar sekmesindeki Profil Ayarları bölümünden profil bilgilerini güncelleyebilirler. Aynı zamanda profil fotoğrafı ekleyip fotoğrafı değiştirebilirler. 

## Sistematik Yaklaşım
[![usecase.png](https://i.postimg.cc/qMy1sz2C/usecase.png)](https://postimg.cc/mtZ3RZXb)  
Şekil 1. Use-Case Diyagramı ile Kullanıcıların Gösterimi  

[![uml.png](https://i.postimg.cc/508X7dgp/uml.png)](https://postimg.cc/njcVrW6Q)  
Şekil 2. Diyagram ile Kullanıcılarla Veritabanı İlişkisi  
[![as.png](https://i.postimg.cc/9QN1VZhK/as.png)](https://postimg.cc/hXTV1Jh1) Şekil 3. Use-Case Diyagramı Gösterimi

## Uygulama Tasarımı
### Giriş Ekranı
[![giris-Ekrani.jpg](https://i.postimg.cc/4dvSJywc/giris-Ekrani.jpg)](https://postimg.cc/9RMpbW6X | width=100)   
Uygulama 2 tip kullanıcıya hitap etmektedir; görme engelli bireyler ve gönüllü bireyler.
### Görme Engelli Kullanıcı Ekranı
[![video.png](https://i.postimg.cc/mkZ8nQzh/video.png)](https://postimg.cc/PpcWpvMk  | width=100)
Görme engelli kullanıcılar, sayfadaki  buton  sayesinde video çekebilirler veya kaydettikleri ses kaydını dinleyebilirler.

### Görme Engelli Kullanıcı Video Yükleme Ekranı   
[![kopya2.jpg](https://i.postimg.cc/bJq7M4jp/kopya2.jpg)](https://postimg.cc/WDfYqW3H | width=100)   
Kullanıcılar çektikleri videoyu sisteme yüklerler.

### Gönüllü Yardım Ekranı
[![g-n-ll.png](https://i.postimg.cc/T2y9KYVz/g-n-ll.png)](https://postimg.cc/3WTp6hb1 | width=100)  
Görme engelli bireyler tarafından yüklenen tüm videolar gönüllü bireyin yardım sayfasında listelenir.Kullanıcı yardım etmek istediği videonun butonuna tıklayarak videodaki probleme ses kaydederek yanıt verir.

### Gönüllü  Ses Kayıt Ekranı
[![yardim.png](https://i.postimg.cc/X7cBwC7h/yardim.png)](https://postimg.cc/Cz5K0d8H | width=100)  
Gönüllü kullanıcılar yardım etmek istedikleri video için ses kaydederler.Bu kayıtlar sisteme yüklenir ve görme engelli bireye ulaştırılır.Bu sayede iletişim gerçekleşmiş ve görme engelli bireyin problemine çözüm bulunmuş olur.





