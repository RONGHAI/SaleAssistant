--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.4.5
-- Started on 2015-12-10 14:50:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 192 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

--- CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2192 (class 0 OID 0)
-- Dependencies: 192
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

-- COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 16735)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE accounts (
    id integer NOT NULL,
    merchant_id integer NOT NULL,
    email text,
    user_name text,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE accounts OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16744)
-- Name: attachments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE attachments (
    id integer NOT NULL,
    mime text NOT NULL,
    extension character varying(20) DEFAULT NULL::character varying,
    content bytea,
    app text,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE attachments OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16754)
-- Name: carriers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE carriers (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    website text NOT NULL,
    track_url text NOT NULL,
    track_method character varying(20) DEFAULT 'GET'::character varying NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE carriers OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 16764)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE categories (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    code character varying(100) DEFAULT NULL::character varying,
    level integer NOT NULL,
    parent_id integer,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE categories OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 16774)
-- Name: client_addresses; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE client_addresses (
    id integer NOT NULL,
    client_id integer NOT NULL,
    address text,
    phone character varying(20) DEFAULT NULL::character varying,
    zip_code character varying(10) DEFAULT NULL::character varying,
    "default" smallint DEFAULT 0::smallint,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE client_addresses OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 16786)
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE clients (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    wangwang character varying(100) DEFAULT NULL::character varying,
    qq integer,
    qq_name character varying(100) DEFAULT NULL::character varying,
    birthday timestamp without time zone,
    gender character varying(1) DEFAULT 'U'::character varying NOT NULL,
    phone character varying(20) DEFAULT NULL::character varying,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE clients OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 16799)
-- Name: currencies; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE currencies (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    code character varying(10) NOT NULL,
    sign character varying(4) NOT NULL,
    exchange_rate numeric(20,8) DEFAULT 1.00000000 NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE currencies OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 16809)
-- Name: hashtag_products; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hashtag_products (
    id integer NOT NULL,
    hashtag_id text NOT NULL,
    product_id text NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE hashtag_products OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 16818)
-- Name: hashtags; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hashtags (
    id integer NOT NULL,
    hashtag character varying(20) NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE hashtags OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16827)
-- Name: invoices; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE invoices (
    id integer NOT NULL,
    order_id integer NOT NULL,
    client_id integer NOT NULL,
    client_address_id integer NOT NULL,
    carrier_id integer NOT NULL,
    track_number character varying(20) NOT NULL,
    status text NOT NULL,
    forward_invoice_id integer,
    shipping_fee numeric(20,8) NOT NULL,
    duty numeric(20,8) DEFAULT 0.00000000 NOT NULL,
    shipping_fee_currency_id integer NOT NULL,
    shipping_time timestamp without time zone,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE invoices OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16837)
-- Name: merchants; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE merchants (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    code character varying(10) DEFAULT NULL::character varying,
    website text NOT NULL,
    track_url text,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE merchants OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16847)
-- Name: navigations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE navigations (
    id integer NOT NULL,
    tier_1 integer NOT NULL,
    tier_2 integer NOT NULL,
    tier_3 integer NOT NULL,
    tier_4 integer NOT NULL,
    worker text,
    label text NOT NULL,
    "order" integer DEFAULT 0,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text,
    i18n character varying(20) DEFAULT NULL::character varying
);


-- ALTER TABLE navigations OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16858)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE orders (
    id integer NOT NULL,
    order_number text NOT NULL,
    order_time timestamp without time zone NOT NULL,
    order_status character varying(50) DEFAULT 'INIT'::character varying NOT NULL,
    client_id integer NOT NULL,
    cost numeric(20,8) NOT NULL,
    discount numeric(20,8) DEFAULT 0.00000000 NOT NULL,
    sale_price numeric(20,8) NOT NULL,
    shipping_fee numeric(20,8) NOT NULL,
    duty numeric(20,8) DEFAULT 0.00000000 NOT NULL,
    currency_id integer NOT NULL,
    sale_price_currency_id integer NOT NULL,
    shipping_fee_currency_id integer NOT NULL,
    net_profit numeric(20,8) DEFAULT 0.00000000 NOT NULL,
    net_profit_currency_id integer NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE orders OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16871)
-- Name: product_categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE product_categories (
    id integer NOT NULL,
    product_id integer NOT NULL,
    category_id integer NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE product_categories OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16880)
-- Name: product_images; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE product_images (
    id integer NOT NULL,
    product_id integer NOT NULL,
    image_id integer,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE product_images OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16889)
-- Name: product_specifications; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE product_specifications (
    id integer NOT NULL,
    product_id integer NOT NULL,
    currency_id integer NOT NULL,
    name character varying(100) NOT NULL,
    code character varying(100) NOT NULL,
    description character varying(100) NOT NULL,
    cost numeric(20,8) DEFAULT NULL::numeric,
    sale_price numeric(20,8) NOT NULL,
    tax numeric(20,8) NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE product_specifications OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16899)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE products (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    code character varying(100) DEFAULT NULL::character varying,
    english_name text NOT NULL,
    chinese_name text NOT NULL,
    description text,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE products OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16909)
-- Name: system_informations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE system_informations (
    id integer NOT NULL,
    code character varying(100) NOT NULL,
    value text NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE system_informations OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 16918)
-- Name: usa_order_items; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usa_order_items (
    id integer NOT NULL,
    usa_order_id integer NOT NULL,
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    product_specification_id integer NOT NULL,
    invoice_id integer NOT NULL,
    amount integer NOT NULL,
    cost numeric(20,8) NOT NULL,
    tax numeric(20,8) NOT NULL,
    shipping_fee numeric(20,8) NOT NULL,
    sale_price numeric(20,8) NOT NULL,
    currency_id integer NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE usa_order_items OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16927)
-- Name: usa_orders; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usa_orders (
    id integer NOT NULL,
    usa_order_number text NOT NULL,
    real_order_number text NOT NULL,
    cost numeric(20,8) NOT NULL,
    tax numeric(20,8) NOT NULL,
    shipping_fee numeric(20,8) NOT NULL,
    discount numeric(20,8) NOT NULL,
    real_cost numeric(20,8) NOT NULL,
    carrier_id integer NOT NULL,
    track_number text,
    currency_id integer NOT NULL,
    account_id integer NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


-- ALTER TABLE usa_orders OWNER TO postgres;

--
-- TOC entry 2165 (class 0 OID 16735)
-- Dependencies: 172
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY accounts (id, merchant_id, email, user_name, disabled, add_time, update_time, note) FROM stdin;
1	3	liweiwei.buy@gmail.com	\N	0	2015-11-04 13:49:44	\N	\N
\.


--
-- TOC entry 2166 (class 0 OID 16744)
-- Dependencies: 173
-- Data for Name: attachments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY attachments (id, mime, extension, content, app, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2167 (class 0 OID 16754)
-- Dependencies: 174
-- Data for Name: carriers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY carriers (id, name, website, track_url, track_method, disabled, add_time, update_time, note) FROM stdin;
1	ANL Express 新干线快递	http://www.anlexpress.com/	http://www.anlexpress.com/index.php/Index-index	POST	0	2013-12-03 12:04:31	2013-12-03 00:00:00	\N
2	EMS 中国	http://www.ems.com.cn	http://www.ems.com.cn	POST	0	2013-12-03 12:06:32	2013-12-03 00:00:00	\N
3	SF Express 顺丰速运	http://www.sf-express.com/cn/sc/	http://www.sf-express.com/cn/sc/	POST	0	2013-12-03 12:08:54	2013-12-03 00:00:00	\N
\.


--
-- TOC entry 2168 (class 0 OID 16764)
-- Dependencies: 175
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY categories (id, name, code, level, parent_id, disabled, add_time, update_time, note) FROM stdin;
1	化妆护肤品	\N	1	-1	0	2015-11-04 13:56:31	2015-11-30 15:47:48	\N
2	宝宝用品	\N	1	-1	0	2015-11-04 13:56:51	2015-11-30 15:47:48	\N
3	宝宝护肤	\N	2	2	0	2015-11-04 13:57:23	2015-11-04 13:57:23	\N
\.


--
-- TOC entry 2169 (class 0 OID 16774)
-- Dependencies: 176
-- Data for Name: client_addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY client_addresses (id, client_id, address, phone, zip_code, "default", disabled, add_time, update_time, note) FROM stdin;
1	2	江苏省宿迁市发展大道86号 民丰银行	13625257441	223800	1	0	2015-11-30 16:23:30	\N	\N
2	3	孙明珠: 江苏省苏州市工业园区华池街圆融时代广场23栋B座N3B309单元	18662187061	215000	1	0	2015-11-30 16:51:09	2015-11-30 16:53:01	\N
3	3	任长青: 江苏省苏州市创意产业园11幢4楼甲骨文软件系统有限公司	15995892096	215000	0	0	2015-11-30 16:51:45	2015-11-30 16:53:01	\N
4	3	焦铁英: 河北省唐山市唐海县建设大街电力大厦家属楼	13832971186	063200	0	0	2015-11-30 16:56:07	2015-11-30 16:57:21	\N
\.


--
-- TOC entry 2170 (class 0 OID 16786)
-- Dependencies: 177
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY clients (id, name, wangwang, qq, qq_name, birthday, gender, phone, disabled, add_time, update_time, note) FROM stdin;
1	徐晓露		401291662	徐晓露	2013-12-04 00:00:00	F		0	2013-12-04 16:17:14	2015-11-04 13:27:08	\N
2	叶烨	\N	493745786	叶烨	\N	F	13625257441	0	2015-11-30 16:22:25	2015-11-30 16:22:25	\N
3	孙明珠	\N	576512468	孙明珠	\N	F	18662187061	0	2015-11-30 16:47:10	2015-11-30 16:50:21	\N
\.


--
-- TOC entry 2171 (class 0 OID 16799)
-- Dependencies: 178
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY currencies (id, name, code, sign, exchange_rate, disabled, add_time, update_time, note) FROM stdin;
1	人民币	CNY	¥	1.00000000	0	2013-12-03 12:16:21	2015-08-06 00:00:00	\N
2	美元	USD	$	1.00000000	0	2013-12-03 12:16:32	2015-08-12 00:00:00	\N
\.


--
-- TOC entry 2172 (class 0 OID 16809)
-- Dependencies: 179
-- Data for Name: hashtag_products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hashtag_products (id, hashtag_id, product_id, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2173 (class 0 OID 16818)
-- Dependencies: 180
-- Data for Name: hashtags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hashtags (id, hashtag, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2174 (class 0 OID 16827)
-- Dependencies: 181
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY invoices (id, order_id, client_id, client_address_id, carrier_id, track_number, status, forward_invoice_id, shipping_fee, duty, shipping_fee_currency_id, shipping_time, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2175 (class 0 OID 16837)
-- Dependencies: 182
-- Data for Name: merchants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY merchants (id, name, code, website, track_url, disabled, add_time, update_time, note) FROM stdin;
1	Amazon	\N	http://amazon.com/	\N	0	2015-11-04 13:47:23	2015-11-04 13:47:23	\N
2	Target	\N	http://www.target.com/	\N	0	2015-11-04 13:47:46	2015-11-04 13:47:46	\N
3	Elizabeth Arden	\N	http://www.elizabetharden.com/	\N	0	2015-11-04 13:48:16	2015-11-04 13:48:16	\N
\.


--
-- TOC entry 2176 (class 0 OID 16847)
-- Dependencies: 183
-- Data for Name: navigations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY navigations (id, tier_1, tier_2, tier_3, tier_4, worker, label, "order", disabled, add_time, update_time, note, i18n) FROM stdin;
1	1	1	0	0	com.weinyc.sa.app.engine.HomeController	主页	0	0	\N	\N	\N	\N
2	1	2	0	0	com.weinyc.sa.app.engine.ClientController	客户	0	0	\N	\N	\N	\N
3	1	0	0	0		基础数据	0	0	\N	\N	\N	\N
4	2	0	0	0		设置	0	0	\N	\N	\N	\N
5	2	1	0	0	com.weinyc.sa.app.engine.NavigationController	导航设置	0	0	\N	\N	\N	\N
6	2	2	0	0	com.weinyc.sa.app.engine.SystemInformationController	系统配置	0	0	\N	\N	\N	\N
7	2	3	0	0	com.weinyc.sa.app.engine.CurrencyController	汇率配置	0	0	\N	\N	\N	\N
8	1	3	0	0	com.weinyc.sa.app.engine.AccountController	账户设置	0	0	\N	\N	\N	\N
9	1	4	0	0	com.weinyc.sa.app.engine.CarrierController	承运人设置	0	0	\N	\N	\N	\N
10	1	6	0	0	com.weinyc.sa.app.engine.MerchantController	商户信息	0	0	\N	\N	\N	\N
11	1	7	0	0	com.weinyc.sa.app.engine.ProductController	商品信息	0	0	\N	\N	\N	\N
12	1	8	0	0	com.weinyc.sa.app.engine.CategoryController	商品类别	0	0	\N	\N	\N	\N
13	2	5	0	0	com.weinyc.sa.app.engine.AttachmentController	文件管理	0	0	\N	\N	\N	\N
\.


--
-- TOC entry 2177 (class 0 OID 16858)
-- Dependencies: 184
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY orders (id, order_number, order_time, order_status, client_id, cost, discount, sale_price, shipping_fee, duty, currency_id, sale_price_currency_id, shipping_fee_currency_id, net_profit, net_profit_currency_id, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2178 (class 0 OID 16871)
-- Dependencies: 185
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product_categories (id, product_id, category_id, disabled, add_time, update_time, note) FROM stdin;
2	1	3	0	\N	\N	\N
\.


--
-- TOC entry 2179 (class 0 OID 16880)
-- Dependencies: 186
-- Data for Name: product_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product_images (id, product_id, image_id, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2180 (class 0 OID 16889)
-- Dependencies: 187
-- Data for Name: product_specifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product_specifications (id, product_id, currency_id, name, code, description, cost, sale_price, tax, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2181 (class 0 OID 16899)
-- Dependencies: 188
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY products (id, name, code, english_name, chinese_name, description, disabled, add_time, update_time, note) FROM stdin;
1	Ceramide Capsules Youth Restoring Serum Set-180 Piece	\N	Ceramide Capsules Youth Restoring Serum Set-180 Piece	伊丽莎白雅顿时空胶囊180粒	\N	0	2015-11-04 14:02:47	2015-11-30 16:58:34	\N
\.


--
-- TOC entry 2182 (class 0 OID 16909)
-- Dependencies: 189
-- Data for Name: system_informations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY system_informations (id, code, value, disabled, add_time, update_time, note) FROM stdin;
1	DEFAULT_CARRIER	1	0	2013-12-03 12:10:14	2013-12-03 00:00:00	默认承运人
2	BASE_CURRENCY	1	0	2013-12-03 12:13:46	2015-08-25 00:00:00	默认货币
3	EXCHANGE_RATE_QUERY_URL	http://rate-exchange.appspot.com/currency?from=%s&to=%s&q=%d	0	2013-12-03 12:15:13	2015-08-05 00:00:00	\N
\.


--
-- TOC entry 2183 (class 0 OID 16918)
-- Dependencies: 190
-- Data for Name: usa_order_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usa_order_items (id, usa_order_id, order_id, product_id, product_specification_id, invoice_id, amount, cost, tax, shipping_fee, sale_price, currency_id, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2184 (class 0 OID 16927)
-- Dependencies: 191
-- Data for Name: usa_orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usa_orders (id, usa_order_number, real_order_number, cost, tax, shipping_fee, discount, real_cost, carrier_id, track_number, currency_id, account_id, disabled, add_time, update_time, note) FROM stdin;
\.


--
-- TOC entry 2017 (class 2606 OID 16743)
-- Name: accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- TOC entry 2019 (class 2606 OID 16753)
-- Name: attachments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY attachments
    ADD CONSTRAINT attachments_pkey PRIMARY KEY (id);


--
-- TOC entry 2021 (class 2606 OID 16763)
-- Name: carriers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY carriers
    ADD CONSTRAINT carriers_pkey PRIMARY KEY (id);


--
-- TOC entry 2023 (class 2606 OID 16773)
-- Name: categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 2025 (class 2606 OID 16785)
-- Name: client_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY client_addresses
    ADD CONSTRAINT client_addresses_pkey PRIMARY KEY (id);


--
-- TOC entry 2027 (class 2606 OID 16798)
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 2029 (class 2606 OID 16808)
-- Name: currencies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY currencies
    ADD CONSTRAINT currencies_pkey PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 16817)
-- Name: hashtag_products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hashtag_products
    ADD CONSTRAINT hashtag_products_pkey PRIMARY KEY (id);


--
-- TOC entry 2033 (class 2606 OID 16826)
-- Name: hashtags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hashtags
    ADD CONSTRAINT hashtags_pkey PRIMARY KEY (id);


--
-- TOC entry 2035 (class 2606 OID 16836)
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (id);


--
-- TOC entry 2037 (class 2606 OID 16846)
-- Name: merchants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY merchants
    ADD CONSTRAINT merchants_pkey PRIMARY KEY (id);


--
-- TOC entry 2039 (class 2606 OID 16857)
-- Name: navigations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY navigations
    ADD CONSTRAINT navigations_pkey PRIMARY KEY (id);


--
-- TOC entry 2041 (class 2606 OID 16870)
-- Name: orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 2043 (class 2606 OID 16879)
-- Name: product_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT product_categories_pkey PRIMARY KEY (id);


--
-- TOC entry 2045 (class 2606 OID 16888)
-- Name: product_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY product_images
    ADD CONSTRAINT product_images_pkey PRIMARY KEY (id);


--
-- TOC entry 2047 (class 2606 OID 16898)
-- Name: product_specifications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY product_specifications
    ADD CONSTRAINT product_specifications_pkey PRIMARY KEY (id);


--
-- TOC entry 2049 (class 2606 OID 16908)
-- Name: products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 2051 (class 2606 OID 16917)
-- Name: system_informations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY system_informations
    ADD CONSTRAINT system_informations_pkey PRIMARY KEY (id);


--
-- TOC entry 2053 (class 2606 OID 16926)
-- Name: usa_order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usa_order_items
    ADD CONSTRAINT usa_order_items_pkey PRIMARY KEY (id);


--
-- TOC entry 2055 (class 2606 OID 16935)
-- Name: usa_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usa_orders
    ADD CONSTRAINT usa_orders_pkey PRIMARY KEY (id);


--
-- TOC entry 2191 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

-- REVOKE ALL ON SCHEMA public FROM PUBLIC;
-- REVOKE ALL ON SCHEMA public FROM postgres;
-- GRANT ALL ON SCHEMA public TO postgres;
-- GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-12-10 14:50:09

--
-- PostgreSQL database dump complete
--

