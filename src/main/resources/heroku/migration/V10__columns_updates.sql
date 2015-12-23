ALTER TABLE ONLY user_navigations
    ADD CONSTRAINT user_navigations_pkey PRIMARY KEY (id);



ALTER TABLE orders ALTER COLUMN order_time SET DEFAULT now();


ALTER TABLE orders ALTER COLUMN currency_id  DROP NOT NULL;
ALTER TABLE orders ALTER COLUMN sale_price_currency_id  DROP NOT NULL;
ALTER TABLE orders ALTER COLUMN shipping_fee_currency_id  DROP NOT NULL;
ALTER TABLE orders ALTER COLUMN net_profit_currency_id  DROP NOT NULL;


/*
ALTER TABLE orders ALTER COLUMN sale_price TYPE money;
ALTER TABLE orders ALTER COLUMN sale_price SET NOT NULL;

*/