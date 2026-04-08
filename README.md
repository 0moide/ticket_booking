# Кинотеатр CINEMA — Система бронирования билетов

Современное веб-приложение для бронирования билетов в кинотеатр с удобным интерфейсом и широкими возможностями фильтрации.

## О проекте

Кинотеатр CINEMA — это полноценное веб-приложение для бронирования билетов в кинотеатр. Пользователи могут просматривать афишу фильмов, выбирать удобные места в зале и бронировать их.

## Технологии

Бэкенд:
- Java 17 — основной язык
- Spring Boot 2.7 — фреймворк
- Spring Data JPA — работа с БД
- PostgreSQL — база данных
- Maven — сборка проекта

Фронтенд:
- React 18 — библиотека для UI
- React Router DOM — маршрутизация
- Axios — HTTP-запросы
- React Hot Toast — уведомления
- React DatePicker — календарь
- CSS3 — стилизация

## Функционал

Для пользователей:
- Просмотр афиши фильмов с постерами и описанием
- Фильтрация по дате (сегодня/завтра/выбор дня в календаре)
- Поиск фильмов по названию
- Фильтрация по жанрам (режимы AND/OR)
- Просмотр сеансов с выбором удобного времени
- Визуальная схема зала с выбором мест
- Бронирование нескольких мест одновременно
- Отмена бронирования по уникальному ключу
- Email-уведомление о бронировании
- Адаптивный дизайн для всех устройств

## Установка и запуск

Требования:
- Java 17+
- Node.js 16+
- PostgreSQL 14+
- Maven 3.8+

Бэкенд:

1. Клонируйте репозиторий
   git clone https://github.com/0moide/ticket_booking.git
   cd ticket_booking

2. Настройте базу данных
   docker-compose up -d
   или создайте БД вручную: createdb cinema_db

3. Настройте конфигурацию в src/main/resources/application.properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/cinema_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password

4. Запустите бэкенд
   mvn clean install
   mvn spring-boot:run

Фронтенд:

1. Перейдите в папку фронтенда
   cd cinema-frontend

2. Установите зависимости
   npm install

3. Запустите приложение
   npm start

4. Откройте браузер
   http://localhost:3000

## Структура проекта

ticket_booking/  
├── src/  
│   ├── main/  
│   │   ├── java/  
│   │   │   └── com/example/mywebsite/  
│   │   │       ├── controllers/     # REST контроллеры  
│   │   │       ├── entities/        # JPA сущности  
│   │   │       ├── services/        # Бизнес-логика  
│   │   │       └── repositories/    # JPA репозитории  
│   │   └── resources/  
│   │       ├── static/              # Статические файлы (постеры)  
│   │       └── application.properties  
│   └── test/                        # Тесты  
├── cinema-frontend/  
│   ├── src/  
│   │   ├── components/              # React компоненты  
│   │   ├── pages/                   # Страницы приложения  
│   │   ├── services/                # API вызовы  
│   │   ├── App.js                   # Главный компонент  
│   │   └── App.css                  # Глобальные стили  
│   └── package.json  
├── docker-compose.yml  
├── pom.xml  
└── README.md  
  
## API Endpoints

Фильмы:
- GET /api/films - получить все фильмы
- GET /api/films/{id} - получить фильм по ID
- GET /api/films/with-sessions - получить фильмы с сеансами

Сеансы и места:
- GET /api/films/{filmId}/sessions/{sessionId}/seats - схема зала
- POST /api/films/{filmId}/sessions/{sessionId}/seats/reserve-multiple - бронирование мест
- POST /api/films/{filmId}/sessions/{sessionId}/seats/unreserve-multiple - отмена бронирования

## Автор

Misha Baryshnikov (0moide)
GitHub: https://github.com/0moide
