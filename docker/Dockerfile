FROM mysql:5.7

MAINTAINER Areumk <anny0739@github.com>

ENV MYSQL_ROOT_PASSWORD money123!
ENV MYSQL_USER master
ENV MYSQL_PASSWORD moeny123!
ENV MYSQL_DATABASE money

COPY /sql /sql/
RUN cp /sql/*.sql  /docker-entrypoint-initdb.d/
RUN cat /usr/share/zoneinfo/Asia/Seoul > /etc/localtime
