--
-- TOC entry 224 (class 1259 OID 25327)
-- Name: combo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.combo (
    id_combo bigint NOT NULL,
    nombre character varying(155),
    activo boolean,
    descripcion_publica text
);


ALTER TABLE public.combo OWNER TO postgres;

CREATE SEQUENCE public.combo_id_combo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.combo_id_combo_seq OWNER TO postgres;



ALTER SEQUENCE public.combo_id_combo_seq OWNED BY public.combo.id_combo;



--
-- TOC entry 225 (class 1259 OID 25334)
-- Name: combo_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.combo_detalle (
    id_combo bigint NOT NULL,
    id_producto bigint NOT NULL,
    cantidad integer DEFAULT 1,
    activo boolean
);


ALTER TABLE public.combo_detalle OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25319)
-- Name: orden; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orden (
    id_orden bigint NOT NULL,
    fecha date DEFAULT now(),
    sucursal character varying(5),
    anulada boolean DEFAULT false
);


ALTER TABLE public.orden OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 25352)
-- Name: orden_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orden_detalle (
    id_orden bigint NOT NULL,
    id_producto_precio bigint NOT NULL,
    cantidad integer DEFAULT 1 NOT NULL,
    precio numeric(6,2),
    observaciones text
);


ALTER TABLE public.orden_detalle OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 25318)
-- Name: orden_id_orden_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orden_id_orden_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orden_id_orden_seq OWNER TO postgres;

--
-- TOC entry 3499 (class 0 OID 0)
-- Dependencies: 222
-- Name: orden_id_orden_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orden_id_orden_seq OWNED BY public.orden.id_orden;


--
-- TOC entry 228 (class 1259 OID 25371)
-- Name: pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pago (
    id_pago bigint NOT NULL,
    id_orden bigint,
    fecha date DEFAULT now(),
    metodo_pago character varying(10) DEFAULT 'EFECTIVO'::character varying,
    referencia text
);


ALTER TABLE public.pago OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 25387)
-- Name: pago_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pago_detalle (
    id_pago_detalle bigint NOT NULL,
    id_pago bigint,
    monto numeric(6,2),
    observaciones text
);


ALTER TABLE public.pago_detalle OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 25386)
-- Name: pago_detalle_id_pago_detalle_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pago_detalle_id_pago_detalle_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pago_detalle_id_pago_detalle_seq OWNER TO postgres;

--
-- TOC entry 3500 (class 0 OID 0)
-- Dependencies: 229
-- Name: pago_detalle_id_pago_detalle_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pago_detalle_id_pago_detalle_seq OWNED BY public.pago_detalle.id_pago_detalle;


--
-- TOC entry 227 (class 1259 OID 25370)
-- Name: pago_id_pago_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pago_id_pago_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pago_id_pago_seq OWNER TO postgres;

--
-- TOC entry 3501 (class 0 OID 0)
-- Dependencies: 227
-- Name: pago_id_pago_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pago_id_pago_seq OWNED BY public.pago.id_pago;


--
-- TOC entry 218 (class 1259 OID 25278)
-- Name: producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto (
    id_producto bigint NOT NULL,
    nombre character varying(155),
    activo boolean DEFAULT true,
    observaciones text
);


ALTER TABLE public.producto OWNER TO postgres;

--
-- TOC entry 3502 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE producto; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.producto IS 'Productos disponibles para consumo';


--
-- TOC entry 219 (class 1259 OID 25287)
-- Name: producto_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto_detalle (
    id_tipo_producto integer NOT NULL,
    id_producto bigint NOT NULL,
    activo boolean DEFAULT true,
    observaciones text
);


ALTER TABLE public.producto_detalle OWNER TO postgres;

--
-- TOC entry 3503 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE producto_detalle; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.producto_detalle IS 'Determina los tipos de producto que aplican para un producto';


--
-- TOC entry 217 (class 1259 OID 25277)
-- Name: producto_id_producto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.producto_id_producto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.producto_id_producto_seq OWNER TO postgres;

--
-- TOC entry 3504 (class 0 OID 0)
-- Dependencies: 217
-- Name: producto_id_producto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.producto_id_producto_seq OWNED BY public.producto.id_producto;


--
-- TOC entry 221 (class 1259 OID 25306)
-- Name: producto_precio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producto_precio (
    id_producto_precio bigint NOT NULL,
    id_producto bigint,
    fecha_desde date DEFAULT now(),
    fecha_hasta date,
    precio_sugerido numeric(8,2)
);


ALTER TABLE public.producto_precio OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 25305)
-- Name: producto_precio_id_producto_precio_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.producto_precio_id_producto_precio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.producto_precio_id_producto_precio_seq OWNER TO postgres;

--
-- TOC entry 3505 (class 0 OID 0)
-- Dependencies: 220
-- Name: producto_precio_id_producto_precio_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.producto_precio_id_producto_precio_seq OWNED BY public.producto_precio.id_producto_precio;


--
-- TOC entry 216 (class 1259 OID 25268)
-- Name: tipo_producto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_producto (
    id_tipo_producto integer NOT NULL,
    nombre character varying(155) NOT NULL,
    activo boolean DEFAULT true,
    observaciones text
);


ALTER TABLE public.tipo_producto OWNER TO postgres;

--
-- TOC entry 3506 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE tipo_producto; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.tipo_producto IS 'Califica los tipos de productos';


--
-- TOC entry 215 (class 1259 OID 25267)
-- Name: tipo_producto_id_tipo_producto_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_producto_id_tipo_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tipo_producto_id_tipo_producto_seq OWNER TO postgres;

--
-- TOC entry 3507 (class 0 OID 0)
-- Dependencies: 215
-- Name: tipo_producto_id_tipo_producto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_producto_id_tipo_producto_seq OWNED BY public.tipo_producto.id_tipo_producto;


--
-- TOC entry 3294 (class 2604 OID 25322)
-- Name: orden id_orden; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden ALTER COLUMN id_orden SET DEFAULT nextval('public.orden_id_orden_seq'::regclass);


--
-- TOC entry 3299 (class 2604 OID 25374)
-- Name: pago id_pago; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago ALTER COLUMN id_pago SET DEFAULT nextval('public.pago_id_pago_seq'::regclass);


--
-- TOC entry 3302 (class 2604 OID 25390)
-- Name: pago_detalle id_pago_detalle; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago_detalle ALTER COLUMN id_pago_detalle SET DEFAULT nextval('public.pago_detalle_id_pago_detalle_seq'::regclass);


--
-- TOC entry 3289 (class 2604 OID 25281)
-- Name: producto id_producto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto ALTER COLUMN id_producto SET DEFAULT nextval('public.producto_id_producto_seq'::regclass);


--
-- TOC entry 3292 (class 2604 OID 25309)
-- Name: producto_precio id_producto_precio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_precio ALTER COLUMN id_producto_precio SET DEFAULT nextval('public.producto_precio_id_producto_precio_seq'::regclass);


--
-- TOC entry 3287 (class 2604 OID 25271)
-- Name: tipo_producto id_tipo_producto; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_producto ALTER COLUMN id_tipo_producto SET DEFAULT nextval('public.tipo_producto_id_tipo_producto_seq'::regclass);


--
-- TOC entry 3486 (class 0 OID 25327)
-- Dependencies: 224
-- Data for Name: combo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3487 (class 0 OID 25334)
-- Dependencies: 225
-- Data for Name: combo_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3485 (class 0 OID 25319)
-- Dependencies: 223
-- Data for Name: orden; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3488 (class 0 OID 25352)
-- Dependencies: 226
-- Data for Name: orden_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3490 (class 0 OID 25371)
-- Dependencies: 228
-- Data for Name: pago; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3492 (class 0 OID 25387)
-- Dependencies: 230
-- Data for Name: pago_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3480 (class 0 OID 25278)
-- Dependencies: 218
-- Data for Name: producto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3481 (class 0 OID 25287)
-- Dependencies: 219
-- Data for Name: producto_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3483 (class 0 OID 25306)
-- Dependencies: 221
-- Data for Name: producto_precio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3478 (class 0 OID 25268)
-- Dependencies: 216
-- Data for Name: tipo_producto; Type: TABLE DATA; Schema: public; Owner: postgres
--

-- Insertar tipos de producto
INSERT INTO public.tipo_producto (id_tipo_producto, nombre, activo, observaciones) VALUES 
(1, 'bebida', true, NULL),
(2, 'comida', true, NULL),
(3, 'tipicos', true, NULL),
(4, 'postres', true, NULL);

-- Insertar productos (comidas típicas, comida rápida, bebidas y postres)
INSERT INTO public.producto (id_producto, nombre, activo, observaciones) VALUES
-- Comidas típicas
(1, 'Pupusa revueltas', true, 'Pupusas de queso, frijol y chicharrón'),
(2, 'Pupusa de queso', true, 'Pupusas de queso'),
(3, 'Pupusa de frijol', true, 'Pupusas de frijol'),
(4, 'Tamales de elote', true, 'Tamales dulces de maíz'),
(5, 'Yuca frita', true, 'Yuca frita con chicharrón y curtido'),
(6, 'Sopa de pata', true, 'Sopa tradicional con pata de res'),
(7, 'Enchiladas', true, 'Tortillas fritas con carne y vegetales'),
(8, 'Atol de elote', true, 'Bebida caliente de maíz dulce'),
-- Comida rápida
(9, 'Hamburguesa clásica', true, 'Hamburguesa con queso y vegetales'),
(10, 'Pizza familiar', true, 'Pizza de 12 porciones'),
(11, 'Hot dog', true, 'Perro caliente con todos los ingredientes'),
(12, 'Papas fritas', true, 'Porción de papas fritas'),
-- Bebidas
(13, 'Coca Cola', true, 'Gaseosa de 500ml'),
(14, 'Pepsi', true, 'Gaseosa de 500ml'),
(15, 'Fanta Naranja', true, 'Gaseosa de naranja 500ml'),
(16, 'Jugo de tamarindo', true, 'Jugo natural de tamarindo'),
(17, 'Jugo de horchata', true, 'Bebida de arroz y semillas'),
(18, 'Cerveza Pilsener', true, 'Cerveza nacional 355ml'),
(19, 'Cerveza Golden', true, 'Cerveza premium 355ml'),
(20, 'Café', true, 'Café negro o con leche'),
-- Postres
(21, 'Quesadilla', true, 'Pan dulce con queso'),
(22, 'Semita', true, 'Postre de piña o dulce de leche'),
(23, 'Pastel tres leches', true, 'Pastel tradicional'),
(24, 'Empanada de platano', true, 'Empanada dulce de plátano');

-- Insertar relación de productos con tipos
INSERT INTO public.producto_detalle (id_tipo_producto, id_producto, activo, observaciones) VALUES
-- Comidas típicas
(3, 1, true, NULL),
(3, 2, true, NULL),
(3, 3, true, NULL),
(3, 4, true, NULL),
(3, 5, true, NULL),
(3, 6, true, NULL),
(3, 7, true, NULL),


-- Bebidas
(1, 8, true, NULL),
(1, 13, true, NULL),
(1, 14, true, NULL),
(1, 15, true, NULL),
(1, 16, true, NULL),
(1, 17, true, NULL),
(1, 18, true, NULL),
(1, 19, true, NULL),
(1, 20, true, NULL),

-- Comida rápida
(2, 9, true, NULL),
(2, 10, true, NULL),
(2, 11, true, NULL),
(2, 12, true, NULL),

-- Postres
(4, 21, true, NULL),
(4, 22, true, NULL),
(4, 23, true, NULL),
(4, 24, true, NULL);


-- Insertar precios de productos
INSERT INTO public.producto_precio (id_producto_precio, id_producto, fecha_desde, fecha_hasta, precio_sugerido) VALUES
-- Comidas típicas
(1, 1, '2025-01-01', NULL, 0.50),
(2, 2, '2025-01-01', NULL, 0.75),
(3, 3, '2025-01-01', NULL, 0.50),
(4, 4, '2025-01-01', NULL, 0.50),
(5, 5, '2025-01-01', NULL, 1.50),
(6, 6, '2025-01-01', NULL, 3.00),
(7, 7, '2025-01-01', NULL, 0.50),
(8, 8, '2025-01-01', NULL, 1.00),
-- Comida rápida
(9, 9, '2025-01-01', NULL, 2.00),
(10, 10, '2025-01-01', NULL, 8.00),
(11, 11, '2025-01-01', NULL, 2.00),
(12, 12, '2025-01-01', NULL, 1.75),
-- Bebidas
(13, 13, '2025-01-01', NULL, 1.25),
(14, 14, '2025-01-01', NULL, 1.20),
(15, 15, '2025-01-01', NULL, 1.20),
(16, 16, '2025-01-01', NULL, 0.50),
(17, 17, '2025-01-01', NULL, 0.50),
(18, 18, '2025-01-01', NULL, 1.50),
(19, 19, '2025-01-01', NULL, 1.30),
(20, 20, '2025-01-01', NULL, 0.50),
-- Postres
(21, 21, '2025-01-01', NULL, 1.25),
(22, 22, '2025-01-01', NULL, 1.00),
(23, 23, '2025-01-01', NULL, 2.50),
(24, 24, '2025-01-01', NULL, 0.50);

-- Insertar combos
INSERT INTO public.combo (id_combo, nombre, activo, descripcion_publica) VALUES
(1, 'Combo pupusero', true, '2 pupusas revueltas + bebida'),
(2, 'Combo desayuno típico', true, 'Tamal de elote + atol de elote'),
(3, 'Combo rápido', true, 'Hamburguesa + papas + gaseosa'),
(4, 'Combo cervecero', true, '2 cervezas + yuca frita');

-- Insertar detalles de combos
INSERT INTO public.combo_detalle (id_combo, id_producto, cantidad, activo) VALUES
-- Combo pupusero
(1, 1, 2, true),
(1, 13, 1, true),
-- Combo desayuno típico
(2, 4, 1, true),
(2, 8, 1, true),
-- Combo rápido
(3, 9, 1, true),
(3, 12, 1, true),
(3, 13, 1, true),
-- Combo cervecero
(4, 18, 2, true),
(4, 5, 1, true);


--
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 222
-- Name: orden_id_orden_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orden_id_orden_seq', 1, false);


--
-- TOC entry 3509 (class 0 OID 0)
-- Dependencies: 229
-- Name: pago_detalle_id_pago_detalle_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pago_detalle_id_pago_detalle_seq', 1, false);


--
-- TOC entry 3510 (class 0 OID 0)
-- Dependencies: 227
-- Name: pago_id_pago_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pago_id_pago_seq', 1, false);


--
-- TOC entry 3511 (class 0 OID 0)
-- Dependencies: 217
-- Name: producto_id_producto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producto_id_producto_seq', 1, false);


--
-- TOC entry 3512 (class 0 OID 0)
-- Dependencies: 220
-- Name: producto_precio_id_producto_precio_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.producto_precio_id_producto_precio_seq', 1, false);


--
-- TOC entry 3513 (class 0 OID 0)
-- Dependencies: 215
-- Name: tipo_producto_id_tipo_producto_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_producto_id_tipo_producto_seq', 3, true);


--
-- TOC entry 3314 (class 2606 OID 25333)
-- Name: combo pk_combo; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.combo
    ADD CONSTRAINT pk_combo PRIMARY KEY (id_combo);


--
-- TOC entry 3318 (class 2606 OID 25339)
-- Name: combo_detalle pk_combo_detalle; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.combo_detalle
    ADD CONSTRAINT pk_combo_detalle PRIMARY KEY (id_combo, id_producto);


--
-- TOC entry 3312 (class 2606 OID 25326)
-- Name: orden pk_orden; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden
    ADD CONSTRAINT pk_orden PRIMARY KEY (id_orden);


--
-- TOC entry 3320 (class 2606 OID 25359)
-- Name: orden_detalle pk_orden_detalle; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_detalle
    ADD CONSTRAINT pk_orden_detalle PRIMARY KEY (id_orden, id_producto_precio);


--
-- TOC entry 3322 (class 2606 OID 25380)
-- Name: pago pk_pago; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pk_pago PRIMARY KEY (id_pago);


--
-- TOC entry 3324 (class 2606 OID 25394)
-- Name: pago_detalle pk_pago_detalle; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago_detalle
    ADD CONSTRAINT pk_pago_detalle PRIMARY KEY (id_pago_detalle);


--
-- TOC entry 3306 (class 2606 OID 25286)
-- Name: producto pk_producto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT pk_producto PRIMARY KEY (id_producto);


--
-- TOC entry 3308 (class 2606 OID 25294)
-- Name: producto_detalle pk_producto_detalle; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_detalle
    ADD CONSTRAINT pk_producto_detalle PRIMARY KEY (id_tipo_producto, id_producto);


--
-- TOC entry 3310 (class 2606 OID 25312)
-- Name: producto_precio pk_producto_precio; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_precio
    ADD CONSTRAINT pk_producto_precio PRIMARY KEY (id_producto_precio);


--
-- TOC entry 3304 (class 2606 OID 25276)
-- Name: tipo_producto pk_tipo_producto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT pk_tipo_producto PRIMARY KEY (id_tipo_producto);


--
-- TOC entry 3315 (class 1259 OID 25345)
-- Name: fki_combo_detalle_combo; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_combo_detalle_combo ON public.combo_detalle USING btree (id_combo);


--
-- TOC entry 3316 (class 1259 OID 25351)
-- Name: fki_fk_combo_detalle_producto; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_fk_combo_detalle_producto ON public.combo_detalle USING btree (id_producto);


--
-- TOC entry 3328 (class 2606 OID 25340)
-- Name: combo_detalle fk_combo_detalle_combo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.combo_detalle
    ADD CONSTRAINT fk_combo_detalle_combo FOREIGN KEY (id_combo) REFERENCES public.combo(id_combo) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3329 (class 2606 OID 25346)
-- Name: combo_detalle fk_combo_detalle_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.combo_detalle
    ADD CONSTRAINT fk_combo_detalle_producto FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3330 (class 2606 OID 25360)
-- Name: orden_detalle fk_orden_detalle_orden; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_detalle
    ADD CONSTRAINT fk_orden_detalle_orden FOREIGN KEY (id_orden) REFERENCES public.orden(id_orden) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3331 (class 2606 OID 25365)
-- Name: orden_detalle fk_orden_detalle_producto_precio; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orden_detalle
    ADD CONSTRAINT fk_orden_detalle_producto_precio FOREIGN KEY (id_producto_precio) REFERENCES public.producto_precio(id_producto_precio) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3332 (class 2606 OID 25381)
-- Name: pago fk_pago_orden; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT fk_pago_orden FOREIGN KEY (id_orden) REFERENCES public.orden(id_orden);


--
-- TOC entry 3325 (class 2606 OID 25295)
-- Name: producto_detalle fk_producto_detalle_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_detalle
    ADD CONSTRAINT fk_producto_detalle_producto FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3326 (class 2606 OID 25300)
-- Name: producto_detalle fk_producto_detalle_tipo_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_detalle
    ADD CONSTRAINT fk_producto_detalle_tipo_producto FOREIGN KEY (id_tipo_producto) REFERENCES public.tipo_producto(id_tipo_producto) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3327 (class 2606 OID 25313)
-- Name: producto_precio fk_producto_precio_producto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producto_precio
    ADD CONSTRAINT fk_producto_precio_producto FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3333 (class 2606 OID 25395)
-- Name: pago_detalle id_pago_detalle_pago; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago_detalle
    ADD CONSTRAINT id_pago_detalle_pago FOREIGN KEY (id_pago) REFERENCES public.pago(id_pago) ON UPDATE RESTRICT ON DELETE CASCADE;


-- Completed on 2025-02-14 23:30:13 UTC

--
-- PostgreSQL database dump complete
--