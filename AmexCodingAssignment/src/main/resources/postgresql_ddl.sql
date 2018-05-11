CREATE TABLE public.person
(
    id numeric NOT NULL,
    name character varying(25) COLLATE pg_catalog."default" NOT NULL,
    age numeric NOT NULL,
    "dateOfBirth" date NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT person_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.person
    OWNER to postgres;