CREATE TABLE IF NOT EXISTS public.data
(
    id uuid NOT NULL,
    location geometry,
    user_id smallint,
    species_name character varying(255),
    observed_datetime timestamp with time zone,
    updated_datetime timestamp with time zone,
    status character varying(255),
    media_url character varying(255),
    CONSTRAINT data_pkey PRIMARY KEY (id)
);

