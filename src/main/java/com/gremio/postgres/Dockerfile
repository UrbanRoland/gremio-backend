FROM postgres:14

COPY postgresql.conf /etc/postgresql/postgresql.conf
COPY init.sql /docker-entrypoint-initdb.d/

RUN mkdir -p /logs/postgres/
RUN chown -R postgres:postgres /logs
USER postgres
