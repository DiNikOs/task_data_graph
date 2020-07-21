Full stack task.

1. Java.
2. Spring boot.
3. Hibernate.
4. FlyWay.
5. H2 BD.
5. Java Script.
6. JQuery.
7. Thymeleaf.
8. Google Graph.
9. Bootstrap 4.

ЗАДАНИЕ:
Необходимо разработать приложение, состоящее из фронтальной и серверной составляющей, которые должны взаимодействовать по REST или GRPC (на усмотрение кандидата). Фронтальное приложение может быть написано на чистом JS, либо jQuery, либо vueJS, либо reactJS (предпочтения нет, равнозначные для компании технологии).

Во фронтальном приложении необходимо на странице реализовать таблицу с возможностью:
- добавления в нее данных (через модальное окно)
- редактирование данных в таблице в inline режиме, т.е. без модального окна, путем редактирования данных непосредственно в таблице

В таблице должны располагаться объекты со следующими полями: дата, инструмент (ценная бумага), стоимость.

Пример данных:
01.01.2019    Газпром    2000
01.01.2019    Автоваз    2500
05.01.2019    Сбербанк   10000
10.01.2019    Газпром    2500
07.10.2019    Автоваз    2100

Т.е. не обязательно по всем датам есть значения для всех инструментов.
На странице должен быть размещен график зависимости стоимости инструментов по датам: по оси абсцисс - даты, по оси ординат – стоимость.
График должен автоматически обновлять данные в зависимости от состояния таблицы.
Серверная часть должна быть реализована на Java. Можно использовать Spring или Spring boot. Желательно чтобы в коде прослеживалось использование функциональности 8-9 java. Данные хранить в H2 либо обычном файле. Для сборки использоваться Maven или Gradle. Если spring - то версия 2.3.
