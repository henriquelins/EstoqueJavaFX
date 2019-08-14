PGDMP     7    +                w            xEstoque    11.3    11.3 *    .           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            /           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            0           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            1           1262    32768    xEstoque    DATABASE     �   CREATE DATABASE "xEstoque" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
    DROP DATABASE "xEstoque";
             postgres    false            �            1259    73774 	   categoria    TABLE     �   CREATE TABLE public.categoria (
    id_categoria integer NOT NULL,
    nome character varying(100) NOT NULL,
    id_setor integer NOT NULL
);
    DROP TABLE public.categoria;
       public         postgres    false            �            1259    73772    categoria_id_categoria_seq    SEQUENCE     �   CREATE SEQUENCE public.categoria_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.categoria_id_categoria_seq;
       public       postgres    false    205            2           0    0    categoria_id_categoria_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;
            public       postgres    false    204            �            1259    49214    movimentacao    TABLE     N  CREATE TABLE public.movimentacao (
    id_movimentacao integer NOT NULL,
    id_produto integer NOT NULL,
    id_usuario integer NOT NULL,
    tipo character varying(50) NOT NULL,
    valor_movimento integer NOT NULL,
    observacoes_movimentacao text NOT NULL,
    quantidade_anterior integer NOT NULL,
    data_da_transacao date
);
     DROP TABLE public.movimentacao;
       public         postgres    false            �            1259    49212     movimentacao_id_movimentacao_seq    SEQUENCE     �   CREATE SEQUENCE public.movimentacao_id_movimentacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.movimentacao_id_movimentacao_seq;
       public       postgres    false    197            3           0    0     movimentacao_id_movimentacao_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.movimentacao_id_movimentacao_seq OWNED BY public.movimentacao.id_movimentacao;
            public       postgres    false    196            �            1259    73751    produto    TABLE     >  CREATE TABLE public.produto (
    id_produto integer NOT NULL,
    nome_produto character varying(100) NOT NULL,
    descricao text NOT NULL,
    setor character varying(50) NOT NULL,
    categoria character varying(50) NOT NULL,
    quantidade integer NOT NULL,
    estoque_minimo integer NOT NULL,
    foto bytea
);
    DROP TABLE public.produto;
       public         postgres    false            �            1259    73749    produto_id_produto_seq    SEQUENCE     �   CREATE SEQUENCE public.produto_id_produto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.produto_id_produto_seq;
       public       postgres    false    201            4           0    0    produto_id_produto_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.produto_id_produto_seq OWNED BY public.produto.id_produto;
            public       postgres    false    200            �            1259    49230    setor    TABLE     ^   CREATE TABLE public.setor (
    id_setor integer NOT NULL,
    nome character varying(100)
);
    DROP TABLE public.setor;
       public         postgres    false            �            1259    49228    setor_id_setor_seq    SEQUENCE     �   CREATE SEQUENCE public.setor_id_setor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.setor_id_setor_seq;
       public       postgres    false    199            5           0    0    setor_id_setor_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.setor_id_setor_seq OWNED BY public.setor.id_setor;
            public       postgres    false    198            �            1259    73766    usuario    TABLE     �   CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nome_usuario character varying(100) NOT NULL,
    login character varying(50) NOT NULL,
    senha character varying(10) NOT NULL,
    acesso integer NOT NULL
);
    DROP TABLE public.usuario;
       public         postgres    false            �            1259    73764    usuario_id_usuario_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.usuario_id_usuario_seq;
       public       postgres    false    203            6           0    0    usuario_id_usuario_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;
            public       postgres    false    202            �
           2604    73777    categoria id_categoria    DEFAULT     �   ALTER TABLE ONLY public.categoria ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_id_categoria_seq'::regclass);
 E   ALTER TABLE public.categoria ALTER COLUMN id_categoria DROP DEFAULT;
       public       postgres    false    204    205    205            �
           2604    49217    movimentacao id_movimentacao    DEFAULT     �   ALTER TABLE ONLY public.movimentacao ALTER COLUMN id_movimentacao SET DEFAULT nextval('public.movimentacao_id_movimentacao_seq'::regclass);
 K   ALTER TABLE public.movimentacao ALTER COLUMN id_movimentacao DROP DEFAULT;
       public       postgres    false    196    197    197            �
           2604    73754    produto id_produto    DEFAULT     x   ALTER TABLE ONLY public.produto ALTER COLUMN id_produto SET DEFAULT nextval('public.produto_id_produto_seq'::regclass);
 A   ALTER TABLE public.produto ALTER COLUMN id_produto DROP DEFAULT;
       public       postgres    false    201    200    201            �
           2604    49233    setor id_setor    DEFAULT     p   ALTER TABLE ONLY public.setor ALTER COLUMN id_setor SET DEFAULT nextval('public.setor_id_setor_seq'::regclass);
 =   ALTER TABLE public.setor ALTER COLUMN id_setor DROP DEFAULT;
       public       postgres    false    199    198    199            �
           2604    73769    usuario id_usuario    DEFAULT     x   ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);
 A   ALTER TABLE public.usuario ALTER COLUMN id_usuario DROP DEFAULT;
       public       postgres    false    202    203    203            +          0    73774 	   categoria 
   TABLE DATA               A   COPY public.categoria (id_categoria, nome, id_setor) FROM stdin;
    public       postgres    false    205   >/       #          0    49214    movimentacao 
   TABLE DATA               �   COPY public.movimentacao (id_movimentacao, id_produto, id_usuario, tipo, valor_movimento, observacoes_movimentacao, quantidade_anterior, data_da_transacao) FROM stdin;
    public       postgres    false    197   [/       '          0    73751    produto 
   TABLE DATA               z   COPY public.produto (id_produto, nome_produto, descricao, setor, categoria, quantidade, estoque_minimo, foto) FROM stdin;
    public       postgres    false    201   0       %          0    49230    setor 
   TABLE DATA               /   COPY public.setor (id_setor, nome) FROM stdin;
    public       postgres    false    199   )0       )          0    73766    usuario 
   TABLE DATA               Q   COPY public.usuario (id_usuario, nome_usuario, login, senha, acesso) FROM stdin;
    public       postgres    false    203   d0       7           0    0    categoria_id_categoria_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.categoria_id_categoria_seq', 1, false);
            public       postgres    false    204            8           0    0     movimentacao_id_movimentacao_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.movimentacao_id_movimentacao_seq', 7, true);
            public       postgres    false    196            9           0    0    produto_id_produto_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.produto_id_produto_seq', 1, false);
            public       postgres    false    200            :           0    0    setor_id_setor_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.setor_id_setor_seq', 2, true);
            public       postgres    false    198            ;           0    0    usuario_id_usuario_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 2, true);
            public       postgres    false    202            �
           2606    49222    movimentacao movimentacao_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT movimentacao_pkey PRIMARY KEY (id_movimentacao);
 H   ALTER TABLE ONLY public.movimentacao DROP CONSTRAINT movimentacao_pkey;
       public         postgres    false    197            �
           2606    73779    categoria pk_categoria 
   CONSTRAINT     ^   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT pk_categoria PRIMARY KEY (id_categoria);
 @   ALTER TABLE ONLY public.categoria DROP CONSTRAINT pk_categoria;
       public         postgres    false    205            �
           2606    49235    setor pk_setor 
   CONSTRAINT     R   ALTER TABLE ONLY public.setor
    ADD CONSTRAINT pk_setor PRIMARY KEY (id_setor);
 8   ALTER TABLE ONLY public.setor DROP CONSTRAINT pk_setor;
       public         postgres    false    199            �
           2606    73759    produto produto_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id_produto);
 >   ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
       public         postgres    false    201            �
           2606    73771    usuario usuario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public         postgres    false    203            �
           1259    73785    categoria_pk    INDEX     Q   CREATE UNIQUE INDEX categoria_pk ON public.categoria USING btree (id_categoria);
     DROP INDEX public.categoria_pk;
       public         postgres    false    205            �
           1259    49236    setor_pk    INDEX     E   CREATE UNIQUE INDEX setor_pk ON public.setor USING btree (id_setor);
    DROP INDEX public.setor_pk;
       public         postgres    false    199            �
           2606    73780 !   categoria categoria_id_setor_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_id_setor_fkey FOREIGN KEY (id_setor) REFERENCES public.setor(id_setor) ON UPDATE CASCADE ON DELETE CASCADE;
 K   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_id_setor_fkey;
       public       postgres    false    199    205    2719            +      x������ � �      #   �   x��Ͻ�  ���)m���p7qu!����@I�R>�/&LjM�n`���վ��2�#�>Ocb^�$����塿Ϝ����&SK]�Cˋ��R���4<S�ێ�
���j9Ilv�Q{�^��߷�R�%4��nvn��h�vB�7��X�      '      x������ � �      %   +   x�3�t.JL�8���ˈ3�� ��$U!������|�=... ���      )   2   x�3�tL����,.)JL�/�LL��44�4�2�,NL,� 3�=... l��     