
CREATE TABLE public.categoria (
    id_categoria integer NOT NULL,
    nome character varying(100),
    id_setor integer NOT NULL
);


--
-- Name: categoria_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.categoria_id_categoria_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: categoria_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;


--
-- Name: logseguranca; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.logseguranca (
    id_logseguranca integer NOT NULL,
    logado character varying(100),
    descricao character varying(100),
    datalog date,
    horalog time without time zone
);


--
-- Name: logseguranca_id_logseguranca_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.logseguranca_id_logseguranca_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: logseguranca_id_logseguranca_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.logseguranca_id_logseguranca_seq OWNED BY public.logseguranca.id_logseguranca;


--
-- Name: movimentacao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.movimentacao (
    id_movimentacao integer NOT NULL,
    id_produto integer NOT NULL,
    id_usuario integer NOT NULL,
    tipo character varying(50),
    valor_movimento integer NOT NULL,
    observacoes_movimentacao text NOT NULL,
    quantidade_anterior integer NOT NULL,
    data_da_transacao date
);


--
-- Name: movimentacao_id_movimentacao_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.movimentacao_id_movimentacao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: movimentacao_id_movimentacao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.movimentacao_id_movimentacao_seq OWNED BY public.movimentacao.id_movimentacao;


--
-- Name: produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.produto (
    id_produto integer NOT NULL,
    nome_produto character varying(100),
    descricao text,
    setor character varying(50),
    categoria character varying(50),
    quantidade integer,
    estoque_minimo integer,
    foto bytea
);


--
-- Name: produto_id_produto_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.produto_id_produto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: produto_id_produto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.produto_id_produto_seq OWNED BY public.produto.id_produto;


--
-- Name: setor; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.setor (
    id_setor integer NOT NULL,
    nome character varying(100)
);


--
-- Name: setor_id_setor_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.setor_id_setor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: setor_id_setor_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.setor_id_setor_seq OWNED BY public.setor.id_setor;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nome_usuario character varying(100),
    login character varying(50),
    senha text,
    acesso integer
);


--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- Name: categoria id_categoria; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_id_categoria_seq'::regclass);


--
-- Name: logseguranca id_logseguranca; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.logseguranca ALTER COLUMN id_logseguranca SET DEFAULT nextval('public.logseguranca_id_logseguranca_seq'::regclass);


--
-- Name: movimentacao id_movimentacao; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao ALTER COLUMN id_movimentacao SET DEFAULT nextval('public.movimentacao_id_movimentacao_seq'::regclass);


--
-- Name: produto id_produto; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.produto ALTER COLUMN id_produto SET DEFAULT nextval('public.produto_id_produto_seq'::regclass);


--
-- Name: setor id_setor; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.setor ALTER COLUMN id_setor SET DEFAULT nextval('public.setor_id_setor_seq'::regclass);


--
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- Name: movimentacao movimentacao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT movimentacao_pkey PRIMARY KEY (id_movimentacao);


--
-- Name: categoria pk_categoria; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT pk_categoria PRIMARY KEY (id_categoria);


--
-- Name: logseguranca pk_logseguranca; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.logseguranca
    ADD CONSTRAINT pk_logseguranca PRIMARY KEY (id_logseguranca);


--
-- Name: usuario pk_operador; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT pk_operador PRIMARY KEY (id_usuario);


--
-- Name: produto pk_produto; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT pk_produto PRIMARY KEY (id_produto);


--
-- Name: setor pk_setor; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.setor
    ADD CONSTRAINT pk_setor PRIMARY KEY (id_setor);


--
-- Name: categoria_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX categoria_pk ON public.categoria USING btree (id_categoria);


--
-- Name: logseguranca_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX logseguranca_pk ON public.logseguranca USING btree (id_logseguranca);


--
-- Name: movimentacao_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX movimentacao_pk ON public.movimentacao USING btree (id_movimentacao);


--
-- Name: produto_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX produto_pk ON public.produto USING btree (id_produto);


--
-- Name: setor_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX setor_pk ON public.setor USING btree (id_setor);


--
-- Name: usuario_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX usuario_pk ON public.usuario USING btree (id_usuario);


--
-- PostgreSQL database dump complete
--

