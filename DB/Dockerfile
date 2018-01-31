FROM mysql:latest

MAINTAINER tilen

ENV MYSQL_DATABASE=xml_processor \
    MYSQL_ROOT_PASSWORD=root

ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306
