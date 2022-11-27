create table images (
                        image_id uuid,
                        image_label varchar(256),
                        image_data bytea,
                        objects_json jsonb,
                        metadata jsonb
);