DROP TABLE IF EXISTS data_test;
CREATE TABLE data_test (
  id                    bigserial,
  name_data             VARCHAR(255),
  created               date,
  count_data            bigint(50)
);

INSERT INTO data_test (id, name_data, created, count_data)
VALUES
(1, 'Газпром', '2019-01-01', 2000),
(2, 'Автоваз', '2019-01-01', 2500),
(3, 'Сбербанк', '2019-01-05', 10000),
(4, 'Газпром', '2019-01-10', 2500),
(5, 'Автоваз', '2019-10-07', 2100);
