# Konferans Planlama Uygulaması

## Giriş
Bu proje, konferans konuşmalarının planlanmasını sağlayan bir Spring Boot uygulamasıdır. Konferans konuşmalarını eklemek ve planlanmış konuşmaları görüntülemek için REST API uç noktaları sağlar.

* Konferansta sabah ve öğleden sonra sunumlar yapılacaktır.
* Hem sabah, hem öğleden sonra aynı anda birden fazla sunum yapılabilir.
* Sabah sunumları saat 9:00'da başlar, 12:00'de biter.
* Saat 12:00'de öğle yemeği olacaktır.
* Öğleden sonra sunumları 13:00'da başlar, iletişim etkinliklerine (networking) kadar sürer. Eğer iletişim etkinliği yoksa saat 17:00'da biter.
* İletişim etkinlikleri sunumlardan zaman kalırsa yapılır. 16:00'dan önce başlayamaz ve en geç 17:00'de biter.
* Sunum süreleri dakika cinsindendir veya "lightning" olarak belirtilir (5 dakika. Bkz:https://en.wikipedia.org/wiki/Lightning_talk).
* Sunumlar arasında mola bulunmamaktadır.

## Proje Yapısı
Projede aşağıdaki ana bileşenler bulunmaktadır:
- **API Katmanı**: REST API uç noktalarını sağlar.
- **Servis Katmanı**: İş mantığını barındırır.
- **DTO (Data Transfer Objects)**: Verilerin taşınmasını sağlar.
- **Entity**: Veritabanı tablolarını temsil eden sınıflar.
- **Validator**: Özel doğrulama anotasyonları ve mantığı.

## Kullanılan Teknolojiler
- Java
- Spring Boot
- Maven
- H2 Database
- JPA/Hibernate
- Jakarta Validation (Bean Validation)
- JUnit ve Mockito (Birim Testleri)

## API Uç Noktaları

### 1. Konuşma Ekleme
#### İstek
- **Yöntem**: POST
- **URL**: `/api/addTalks`
- **İçerik Tipi**: `application/json`
- **İstek Gövdesi**:
```json
{
    "talks": [
        {"title": "Architecting Your Codebase", "duration": 15},
        {"title": "Overdoing it in Python", "duration": 45},
        {"title": "Flavors of Concurrency in Java", "duration": 30},
        {"title": "Ruby Errors from Mismatched Gem Versions", "duration": 45},
        {"title": "Microservices \"Just Right\"", "duration": 60},
        {"title": "JUnit 5 - Shaping the Future of Testing on the JVM", "duration": 45},
        {"title": "Cloud Native Java", "duration": 5},
        {"title": "Communicating Over Distance", "duration": 60},
        {"title": "AWS Technical Essentials", "duration": 45},
        {"title": "Continuous Delivery", "duration": 30},
        {"title": "Monitoring Reactive Applications", "duration": 30},
        {"title": "Pair Programming vs Noise", "duration": 45},
        {"title": "Rails Magic", "duration": 60},
        {"title": "Clojure Ate Scala (on my project)", "duration": 45},
        {"title": "Perfect Scalability", "duration": 30},
        {"title": "Apache Spark", "duration": 30},
        {"title": "Async Testing on JVM", "duration": 60},
        {"title": "A World Without HackerNews", "duration": 30},
        {"title": "User Interface CSS in Apps", "duration": 30},
        {"title": "Advanced JavaScript Techniques", "duration": 60},
        {"title": "Building Scalable Apps with Node.js", "duration": 45},
        {"title": "Introduction to Machine Learning", "duration": 30},
        {"title": "Kubernetes for Developers", "duration": 45},
        {"title": "Effective Docker Usage", "duration": 60},
        {"title": "Spring Boot in Practice", "duration": 45},
        {"title": "Lightning Talk: DevOps Essentials", "duration": 5},
        {"title": "Understanding React Hooks", "duration": 60},
        {"title": "GraphQL vs REST APIs", "duration": 45},
        {"title": "Functional Programming in JavaScript", "duration": 30},
        {"title": "Microservices Architecture", "duration": 60},
        {"title": "Securing Web Applications", "duration": 45},
        {"title": "Lightning Talk: Serverless Concepts", "duration": 5},
        {"title": "Optimizing SQL Queries", "duration": 60},
        {"title": "Event-Driven Microservices", "duration": 45},
        {"title": "Agile Methodologies", "duration": 30},
        {"title": "Data Visualization with D3.js", "duration": 45},
        {"title": "Building APIs with GraphQL", "duration": 60},
        {"title": "Cloud Security Basics", "duration": 45},
        {"title": "Performance Tuning in Java", "duration": 30},
        {"title": "Introduction to TypeScript", "duration": 30},
        {"title": "Advanced Python Programming", "duration": 60},
        {"title": "Design Patterns in C#", "duration": 45},
        {"title": "Testing Strategies for Microservices", "duration": 30},
        {"title": "Developing Mobile Apps with Flutter", "duration": 60},
        {"title": "Blockchain Basics", "duration": 45},
        {"title": "Lightning Talk: WebAssembly", "duration": 5},
        {"title": "Deep Dive into Docker", "duration": 60},
        {"title": "Modern Frontend Development", "duration": 45},
        {"title": "Introduction to DevOps", "duration": 30},
        {"title": "Building Resilient Systems", "duration": 60}
    ]
}

