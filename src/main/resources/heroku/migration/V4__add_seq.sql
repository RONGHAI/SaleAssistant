CREATE SEQUENCE accounts_id_seq;
ALTER TABLE accounts ALTER COLUMN id SET DEFAULT nextval('accounts_id_seq');
ALTER TABLE accounts ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE accounts_id_seq OWNED BY accounts.id;    -- 8.2 or later
SELECT MAX(id) FROM accounts;
SELECT setval('accounts_id_seq', 8);  


CREATE SEQUENCE attachments_id_seq;
ALTER TABLE attachments ALTER COLUMN id SET DEFAULT nextval('attachments_id_seq');
ALTER TABLE attachments ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE attachments_id_seq OWNED BY attachments.id;    -- 8.2 or later
SELECT MAX(id) FROM attachments;
SELECT setval('attachments_id_seq', 8);  


CREATE SEQUENCE carriers_id_seq;
ALTER TABLE carriers ALTER COLUMN id SET DEFAULT nextval('carriers_id_seq');
ALTER TABLE carriers ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE carriers_id_seq OWNED BY carriers.id;    -- 8.2 or later
SELECT MAX(id) FROM carriers;
SELECT setval('carriers_id_seq', 8);  


CREATE SEQUENCE categories_id_seq;
ALTER TABLE categories ALTER COLUMN id SET DEFAULT nextval('categories_id_seq');
ALTER TABLE categories ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE categories_id_seq OWNED BY categories.id;    -- 8.2 or later
SELECT MAX(id) FROM categories;
SELECT setval('categories_id_seq', 8);  


CREATE SEQUENCE client_addresses_id_seq;
ALTER TABLE client_addresses ALTER COLUMN id SET DEFAULT nextval('client_addresses_id_seq');
ALTER TABLE client_addresses ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE client_addresses_id_seq OWNED BY client_addresses.id;    -- 8.2 or later
SELECT MAX(id) FROM client_addresses;
SELECT setval('client_addresses_id_seq', 8);  


CREATE SEQUENCE clients_id_seq;
ALTER TABLE clients ALTER COLUMN id SET DEFAULT nextval('clients_id_seq');
ALTER TABLE clients ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE clients_id_seq OWNED BY clients.id;    -- 8.2 or later
SELECT MAX(id) FROM clients;
SELECT setval('clients_id_seq', 8);  


CREATE SEQUENCE currencies_id_seq;
ALTER TABLE currencies ALTER COLUMN id SET DEFAULT nextval('currencies_id_seq');
ALTER TABLE currencies ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE currencies_id_seq OWNED BY currencies.id;    -- 8.2 or later
SELECT MAX(id) FROM currencies;
SELECT setval('currencies_id_seq', 8);  


CREATE SEQUENCE hashtag_products_id_seq;
ALTER TABLE hashtag_products ALTER COLUMN id SET DEFAULT nextval('hashtag_products_id_seq');
ALTER TABLE hashtag_products ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE hashtag_products_id_seq OWNED BY hashtag_products.id;    -- 8.2 or later
SELECT MAX(id) FROM hashtag_products;
SELECT setval('hashtag_products_id_seq', 8);  


CREATE SEQUENCE hashtags_id_seq;
ALTER TABLE hashtags ALTER COLUMN id SET DEFAULT nextval('hashtags_id_seq');
ALTER TABLE hashtags ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE hashtags_id_seq OWNED BY hashtags.id;    -- 8.2 or later
SELECT MAX(id) FROM hashtags;
SELECT setval('hashtags_id_seq', 8);  


CREATE SEQUENCE invoices_id_seq;
ALTER TABLE invoices ALTER COLUMN id SET DEFAULT nextval('invoices_id_seq');
ALTER TABLE invoices ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE invoices_id_seq OWNED BY invoices.id;    -- 8.2 or later
SELECT MAX(id) FROM invoices;
SELECT setval('invoices_id_seq', 8);  


CREATE SEQUENCE merchants_id_seq;
ALTER TABLE merchants ALTER COLUMN id SET DEFAULT nextval('merchants_id_seq');
ALTER TABLE merchants ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE merchants_id_seq OWNED BY merchants.id;    -- 8.2 or later
SELECT MAX(id) FROM merchants;
SELECT setval('merchants_id_seq', 8);  


CREATE SEQUENCE navigations_id_seq;
ALTER TABLE navigations ALTER COLUMN id SET DEFAULT nextval('navigations_id_seq');
ALTER TABLE navigations ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE navigations_id_seq OWNED BY navigations.id;    -- 8.2 or later
SELECT MAX(id) FROM navigations;
SELECT setval('navigations_id_seq', 8);  


CREATE SEQUENCE orders_id_seq;
ALTER TABLE orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq');
ALTER TABLE orders ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE orders_id_seq OWNED BY orders.id;    -- 8.2 or later
SELECT MAX(id) FROM orders;
SELECT setval('orders_id_seq', 8);  


CREATE SEQUENCE product_categories_id_seq;
ALTER TABLE product_categories ALTER COLUMN id SET DEFAULT nextval('product_categories_id_seq');
ALTER TABLE product_categories ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE product_categories_id_seq OWNED BY product_categories.id;    -- 8.2 or later
SELECT MAX(id) FROM product_categories;
SELECT setval('product_categories_id_seq', 8);  


CREATE SEQUENCE product_images_id_seq;
ALTER TABLE product_images ALTER COLUMN id SET DEFAULT nextval('product_images_id_seq');
ALTER TABLE product_images ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE product_images_id_seq OWNED BY product_images.id;    -- 8.2 or later
SELECT MAX(id) FROM product_images;
SELECT setval('product_images_id_seq', 8);  


CREATE SEQUENCE product_specifications_id_seq;
ALTER TABLE product_specifications ALTER COLUMN id SET DEFAULT nextval('product_specifications_id_seq');
ALTER TABLE product_specifications ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE product_specifications_id_seq OWNED BY product_specifications.id;    -- 8.2 or later
SELECT MAX(id) FROM product_specifications;
SELECT setval('product_specifications_id_seq', 8);  


CREATE SEQUENCE products_id_seq;
ALTER TABLE products ALTER COLUMN id SET DEFAULT nextval('products_id_seq');
ALTER TABLE products ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE products_id_seq OWNED BY products.id;    -- 8.2 or later
SELECT MAX(id) FROM products;
SELECT setval('products_id_seq', 8);  


CREATE SEQUENCE system_informations_id_seq;
ALTER TABLE system_informations ALTER COLUMN id SET DEFAULT nextval('system_informations_id_seq');
ALTER TABLE system_informations ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE system_informations_id_seq OWNED BY system_informations.id;    -- 8.2 or later
SELECT MAX(id) FROM system_informations;
SELECT setval('system_informations_id_seq', 8);  


CREATE SEQUENCE usa_order_items_id_seq;
ALTER TABLE usa_order_items ALTER COLUMN id SET DEFAULT nextval('usa_order_items_id_seq');
ALTER TABLE usa_order_items ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE usa_order_items_id_seq OWNED BY usa_order_items.id;    -- 8.2 or later
SELECT MAX(id) FROM usa_order_items;
SELECT setval('usa_order_items_id_seq', 8);  


CREATE SEQUENCE usa_orders_id_seq;
ALTER TABLE usa_orders ALTER COLUMN id SET DEFAULT nextval('usa_orders_id_seq');
ALTER TABLE usa_orders ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE usa_orders_id_seq OWNED BY usa_orders.id;    -- 8.2 or later
SELECT MAX(id) FROM usa_orders;
SELECT setval('usa_orders_id_seq', 8);  


