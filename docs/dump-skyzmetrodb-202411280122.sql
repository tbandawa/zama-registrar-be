PGDMP                  
    |            skyzmetrodb    16.3    16.3 E    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16392    skyzmetrodb    DATABASE     w   CREATE DATABASE skyzmetrodb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_ZW.UTF-8';
    DROP DATABASE skyzmetrodb;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    17349    audio_source    TABLE     �   CREATE TABLE public.audio_source (
    id bigint NOT NULL,
    auth_token character varying(255),
    stream_url character varying(255)
);
     DROP TABLE public.audio_source;
       public         heap    postgres    false    4            �            1259    17348    audio_source_id_seq    SEQUENCE     |   CREATE SEQUENCE public.audio_source_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.audio_source_id_seq;
       public          postgres    false    4    216            �           0    0    audio_source_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.audio_source_id_seq OWNED BY public.audio_source.id;
          public          postgres    false    215            �            1259    17358    episodes    TABLE     �   CREATE TABLE public.episodes (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    episode integer NOT NULL,
    podcast_id bigint,
    title character varying(150)
);
    DROP TABLE public.episodes;
       public         heap    postgres    false    4            �            1259    17357    episodes_id_seq    SEQUENCE     x   CREATE SEQUENCE public.episodes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.episodes_id_seq;
       public          postgres    false    218    4            �           0    0    episodes_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.episodes_id_seq OWNED BY public.episodes.id;
          public          postgres    false    217            �            1259    17365    news    TABLE     �   CREATE TABLE public.news (
    id bigint NOT NULL,
    author character varying(255),
    content text[],
    created timestamp without time zone NOT NULL,
    published integer NOT NULL,
    title character varying(255)
);
    DROP TABLE public.news;
       public         heap    postgres    false    4            �            1259    17364    news_id_seq    SEQUENCE     t   CREATE SEQUENCE public.news_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.news_id_seq;
       public          postgres    false    4    220            �           0    0    news_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.news_id_seq OWNED BY public.news.id;
          public          postgres    false    219            �            1259    17374    podcasts    TABLE     �   CREATE TABLE public.podcasts (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    description character varying(500),
    published boolean NOT NULL,
    title character varying(150)
);
    DROP TABLE public.podcasts;
       public         heap    postgres    false    4            �            1259    17373    podcasts_id_seq    SEQUENCE     x   CREATE SEQUENCE public.podcasts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.podcasts_id_seq;
       public          postgres    false    222    4            �           0    0    podcasts_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.podcasts_id_seq OWNED BY public.podcasts.id;
          public          postgres    false    221            �            1259    17383    roles    TABLE     W   CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(10)
);
    DROP TABLE public.roles;
       public         heap    postgres    false    4            �            1259    17382    roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          postgres    false    4    224            �           0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          postgres    false    223            �            1259    17390    shows    TABLE       CREATE TABLE public.shows (
    id bigint NOT NULL,
    created timestamp without time zone NOT NULL,
    show_date date,
    description character varying(500),
    end_time time without time zone,
    start_time time without time zone,
    title character varying(150)
);
    DROP TABLE public.shows;
       public         heap    postgres    false    4            �            1259    17389    shows_id_seq    SEQUENCE     u   CREATE SEQUENCE public.shows_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.shows_id_seq;
       public          postgres    false    226    4            �           0    0    shows_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.shows_id_seq OWNED BY public.shows.id;
          public          postgres    false    225            �            1259    17398 
   user_roles    TABLE     ^   CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false    4            �            1259    17404    user_tokens    TABLE     w   CREATE TABLE public.user_tokens (
    id bigint NOT NULL,
    auth_token character varying(255),
    user_id bigint
);
    DROP TABLE public.user_tokens;
       public         heap    postgres    false    4            �            1259    17403    user_tokens_id_seq    SEQUENCE     {   CREATE SEQUENCE public.user_tokens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.user_tokens_id_seq;
       public          postgres    false    229    4            �           0    0    user_tokens_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.user_tokens_id_seq OWNED BY public.user_tokens.id;
          public          postgres    false    228            �            1259    17411    users    TABLE     �   CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255),
    firstname character varying(255),
    isactive integer NOT NULL,
    lastname character varying(255),
    password character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            �            1259    17410    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    231    4            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    230            �           2604    17352    audio_source id    DEFAULT     r   ALTER TABLE ONLY public.audio_source ALTER COLUMN id SET DEFAULT nextval('public.audio_source_id_seq'::regclass);
 >   ALTER TABLE public.audio_source ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216            �           2604    17361    episodes id    DEFAULT     j   ALTER TABLE ONLY public.episodes ALTER COLUMN id SET DEFAULT nextval('public.episodes_id_seq'::regclass);
 :   ALTER TABLE public.episodes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            �           2604    17368    news id    DEFAULT     b   ALTER TABLE ONLY public.news ALTER COLUMN id SET DEFAULT nextval('public.news_id_seq'::regclass);
 6   ALTER TABLE public.news ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            �           2604    17377    podcasts id    DEFAULT     j   ALTER TABLE ONLY public.podcasts ALTER COLUMN id SET DEFAULT nextval('public.podcasts_id_seq'::regclass);
 :   ALTER TABLE public.podcasts ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222            �           2604    17386    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223    224            �           2604    17393    shows id    DEFAULT     d   ALTER TABLE ONLY public.shows ALTER COLUMN id SET DEFAULT nextval('public.shows_id_seq'::regclass);
 7   ALTER TABLE public.shows ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    226    225    226            �           2604    17407    user_tokens id    DEFAULT     p   ALTER TABLE ONLY public.user_tokens ALTER COLUMN id SET DEFAULT nextval('public.user_tokens_id_seq'::regclass);
 =   ALTER TABLE public.user_tokens ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    228    229    229            �           2604    17414    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    231    230    231            �          0    17349    audio_source 
   TABLE DATA           B   COPY public.audio_source (id, auth_token, stream_url) FROM stdin;
    public          postgres    false    216   �I       �          0    17358    episodes 
   TABLE DATA           K   COPY public.episodes (id, created, episode, podcast_id, title) FROM stdin;
    public          postgres    false    218   J       �          0    17365    news 
   TABLE DATA           N   COPY public.news (id, author, content, created, published, title) FROM stdin;
    public          postgres    false    220   �J       �          0    17374    podcasts 
   TABLE DATA           N   COPY public.podcasts (id, created, description, published, title) FROM stdin;
    public          postgres    false    222   �Z       �          0    17383    roles 
   TABLE DATA           )   COPY public.roles (id, name) FROM stdin;
    public          postgres    false    224   �[       �          0    17390    shows 
   TABLE DATA           a   COPY public.shows (id, created, show_date, description, end_time, start_time, title) FROM stdin;
    public          postgres    false    226   �[       �          0    17398 
   user_roles 
   TABLE DATA           6   COPY public.user_roles (user_id, role_id) FROM stdin;
    public          postgres    false    227   �a       �          0    17404    user_tokens 
   TABLE DATA           >   COPY public.user_tokens (id, auth_token, user_id) FROM stdin;
    public          postgres    false    229   �a       �          0    17411    users 
   TABLE DATA           S   COPY public.users (id, email, firstname, isactive, lastname, password) FROM stdin;
    public          postgres    false    231   �b       �           0    0    audio_source_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.audio_source_id_seq', 1, true);
          public          postgres    false    215            �           0    0    episodes_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.episodes_id_seq', 4, true);
          public          postgres    false    217            �           0    0    news_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.news_id_seq', 14, true);
          public          postgres    false    219            �           0    0    podcasts_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.podcasts_id_seq', 1, true);
          public          postgres    false    221            �           0    0    roles_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.roles_id_seq', 1, false);
          public          postgres    false    223            �           0    0    shows_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.shows_id_seq', 11, true);
          public          postgres    false    225            �           0    0    user_tokens_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.user_tokens_id_seq', 17, true);
          public          postgres    false    228            �           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 1, false);
          public          postgres    false    230            �           2606    17356    audio_source audio_source_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.audio_source
    ADD CONSTRAINT audio_source_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.audio_source DROP CONSTRAINT audio_source_pkey;
       public            postgres    false    216            �           2606    17363    episodes episodes_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.episodes
    ADD CONSTRAINT episodes_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.episodes DROP CONSTRAINT episodes_pkey;
       public            postgres    false    218            �           2606    17372    news news_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.news
    ADD CONSTRAINT news_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.news DROP CONSTRAINT news_pkey;
       public            postgres    false    220            �           2606    17381    podcasts podcasts_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.podcasts
    ADD CONSTRAINT podcasts_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.podcasts DROP CONSTRAINT podcasts_pkey;
       public            postgres    false    222            �           2606    17388    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    224            �           2606    17397    shows shows_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.shows
    ADD CONSTRAINT shows_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.shows DROP CONSTRAINT shows_pkey;
       public            postgres    false    226            �           2606    17420 !   users uk6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7;
       public            postgres    false    231            �           2606    17402    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    227    227            �           2606    17409    user_tokens user_tokens_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.user_tokens
    ADD CONSTRAINT user_tokens_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.user_tokens DROP CONSTRAINT user_tokens_pkey;
       public            postgres    false    229            �           2606    17418    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    231            �           2606    17426 &   user_roles fkh8ciramu9cc9q3qcqiv4ue8a6    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6;
       public          postgres    false    227    224    4324            �           2606    17431 &   user_roles fkhfh9dx7w3ubf1co1vdev94g3f    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f;
       public          postgres    false    4334    227    231            �           2606    17421 $   episodes fknetidsqw18chu709udfuyqdw5    FK CONSTRAINT     �   ALTER TABLE ONLY public.episodes
    ADD CONSTRAINT fknetidsqw18chu709udfuyqdw5 FOREIGN KEY (podcast_id) REFERENCES public.podcasts(id);
 N   ALTER TABLE ONLY public.episodes DROP CONSTRAINT fknetidsqw18chu709udfuyqdw5;
       public          postgres    false    222    218    4322            �   5   x�3���())(����L�O�,JL���3���s���K�Rs�r��b���� � �      �   o   x�e�1
�0F�z��@B��hL.���l�����H�����k?H���d�R%Vt�qލ�i,o�*9%�h:����N����h�D���67�7��Y��g֖f��|� �      �      x��\�nI�>KO݇A7@sDZ�e�h�-kZ�K�FczQUQ�feTgf��],�w؋p_���$��,R��>4fv����DV�O�g�99�!��WX����Ը������L Gk ��4��
�� ����Y㍅I��bE��j��/^�`>�'�%WX#�������{xuu��B���M�������������q��uv㯇e��e����hz�h2y4}
�����������`r�|�������5�.K����sv��u�W������Tg����DB�fe
b(�l k�&�	�G��"p�K@��{�P���^�7+Z���m[ ܾ� ���kh,v�?Ǳ��Y!D�&:���T�BF�|\ B���u�Y��`�v)���	z�k���5vc�-��)��maA"Cf1_��;n!G�0�sƺ���`�&'؍`��x�\d���\1�n~��ݡ� >�H����S& ���v���h�AF����i��p���~<P�ӳ���ѩ��&�%��*x�i��Y����w2���u�&�'�]cr`7���-�%L�y����'��[�T$�Ra��W\S�KoW���Z��n�7a�C��\c��sqM.v���1�c�n~��.E��qKBٗ���%\�U����;a��X���0=�<��j���bE�3�X��n~p�����G2Z�(L�=�� M��Z$Zg	G+z0����$| ��Φ'�'�N&�)ܰ[�e����I~��[�6��ye��q�5�'�dQ����>!omՎ�YTT zn]���9�ٷ�:����a/<�F1�k���5+��ULc�������1t�Fie�\���+�'G��j����ɿ�D�����yE�l�C�_�<�p\`�zYCF���%�A5��6=Dz�����W";�U�
�2BF� O�\D�2y%r�on��^����D/��"}ƍ�݂jv�IK_�_�X�Q��IE^s��+n�.�|��)�e�#��ɒ�]�l$�;�5pq���Ł��y.~LϦ������Ee�i��W�%X�`2�I �%'�r=-k,$�Ɠ�d����L^���"���m��:��J�O0�Aa�����N|��8s�N��h%��/$��qI�@���i�H�5�_��8��bepv!4�V��rO��|\�s��<�эD���B�򣑣`&���j��HA��n~��)9|x]Bέ��!_R��n~��|=li�Ұ�͖ز'g����c9W��,3?k�A�^몃��� �	* � ���
jvK��>I�.3ܘ\&�[�v�����=g�)�����Ƹ�hj�%=�n��-��Ϲ�E�4�%5 ��F\�q%�:�Y�ꂌ8!�v��S.�3�7�Ƚ��C+�Er9m7���Ҍ����/E46��X���'+��BW�7���@;;�s��|"��(�2�����^aH>�i3krہ���}���J�9�TEVIT�/��j�S��m�)��.�ށ�64�
*�k}5��z�$��FyVv��+�s�f:p�uM.���C�:0t`�e���D�O'O&��
v:��ٙ��qr�A��eLGϞ1x
o����Ή�I�9�	
�K�Y�R����&��)%EAF9��u�r^80�J����*B�1Wp	����9��=�_����H��}#52��$Bv)��X�+(L��Nˉ?-'��PP�fE�ƪʣ̧2o;X�����:���$�sy3�~л���z�!BG�ԄEc<�hd�ʸ"Q���ه��@Ձ�U�T��Q��w|zv�x|�D
����D�� y"��-%����.��4�ܺ�Y�}�^I����5��s�����
��(�$HБ�����W�RLO�I�Vb�а+Rb�����G�u9I���o<�@�F$��ٵ��֦����TfQٝ4�6�I�>'�u��@Z�U��^q�L������JB��}���G�fm��!��»��eO����Z�HX"�,�$������ѱ.|��0�ɛ�ϛ5I �lV�߁��������Otzv|<>=�Lι��Q�#-�Q1u�M��
u!3EѣJe9�b�M���%+��;Öd:��7y8�}9�.��`�2ڂy�В �&GX���6����L�}����t��Y���^�Oc��z�ѬH�S����Y8hy�B<�A['���ӣ�j��5A4�׬��on��Y���N��I
��i!��yE�2��5�c�>ڄk� �G��F�
rBޝ� ��5�n���P�+q���S����HI�g�&��w!�`7!L��O�,�6H����	����.^�&�<��û���,�
��d�E���	�|�� x��R�jo��]����?�Ŧ?�$TGh�O�F�p��F.RZ�^����h��s �BcE �0�[c)��/�Ig��)I��b�ȻC�d={������b0s�F���bg���ڔ1!E|Ms-�=k�NK�}��@�O)�@G'g�'�ӧ������8�=�h��'��x�\r}�x(qŭ7� (&8�o%8�L���/ugѸV���%&I	���c�\h�[�Ma��n>�.G�:x��R�i��棢�3/��D+-(&@���We&{��X�S�?<�Y�s�Y�S�H��(��4��Ό�~�]2��c��;�#���La/6�7!ga<%Bl7� �-���VY�+��t#E����g�E�v��S!���A��ڔ2,�3.:}�>���n>��(�L�w�K&
��������+".<�����h�e�Q?�Br[aZ�M�2��H���D�/O�t{�bDb�WK��M2畞?�B�+He���)���/�$nQ+G
��	�WHG��`�d��L�$֯��m?�B>�u��C݋�n<�M���6��_���j�J��|��,��*�0��
Y�L�0;�]�4�H����2�Ȫ�7��V��?k���	k���2�5�v�����0;��:Dϒ��,����ъsk�:�>�@��<�{J���d� kT�Ty2�&�����\G���]����ct�r�-/|4g�2��]��B��Er��]�}N6BK�;�Ӻ����!��TB/f����o�x��,�`��7X���o�x��,�`��}-ރ,��l�x|*m�s��Me(�"^Nk��Y�uB��I̓��=Q:�|���9˕
=�6T�*�gw����K8��{N����n�s{��{T�pm_��3��͇�J* �Wh�wo8n���K��$�73���j'�����w��K)���f^�vӇ�+��
�(-��&���O�]QF�� �H��i5(zG����g�=� ���Z��O.w���j��^��D��,�F��ӣ�	\���,�Rd����֩eR~4mT����-ͨ=�L1[{F�V���\��j�R�9�ɛޝE̗Vm:�)=n9��{MWG#�VK�M�^Qq�ڐg��i��K01�`�]tA!�&K �XcPͨz�YA+�ʂ*&�y+08�Ejz���7T&5��I}�T�iD��O�{$=��Z���u��&'�g��v�"#���=-(1��ވ�����p���h�w�w_]�D!j�%X��Ys�P�Q�#�z�����0�m�`x����l7.z?�V@��{��C��C��(ͬ�}|+8�9�K�I�*x&`έWuU��v��'n��~�K�n�!p��_�Z�g�Be��,��[�W�ɷ�g,r��KXB'��*����=�v;�����rYˣ�]N���m���N�D�ٕ ���V��ې����3"Y�!�������/ݡw�R1��$G*�#�<��o�kK���f�LNю,��3d�`1�#��B"S9W��.�#mG�9�F%R�7:�؁�������_S����Ъ��Ju���y�G3����Ц�����)Z�/���Ɒ��,��@��~�KF�#+��{z��߾�%E�]�){�M�{B�O��wмA��4oмA��4oмA���4� 6   �S�d"��9U�($�LtPo�j��V�����z��k���?a��������N�u�      �   �   x�U�1k�0�g�W��c"�P�-]J�����ٕ���O�'ȯ/��v�����T�`_������d'c��}��>��QƎ&��K|H���Ƚ+�N���@��(�$��e���dp
��+�.1	vZ���b*=������Y�G ���c�ػf��xp8�n3X�Z���lW��2�Q誼û���wW��/?U_      �      x�3�LL�������� $�      �   �  x�}V[s�8~�_�j���\xZ:$��t�{��~ٗ�}�5�:�$����-	���� F��v�Ag��o���w��d��'������;?��jSxԌZ*�a#Ω]���;y('؈d �ǂ<^���Ï�������y�W%CLK�*�Cͼ�t��r�q�,WZs�Z�55^�ʝ�=���B�����p�5;��*6�3�[ΒN8����~'~���Pm�8�;<g�v�H&��G�-�-^�
��<\��4R�I�3�1^,��գ�)aYٽ��u�*ݲŢҌ�UQY�Qs2J���ʳn��V{�p>��e�ye�A�v���^/��uxU&�l�*/�������b0A�d��'�9Ús���d)9x�޳Y�L�Ú�V�P�+^�5[̾���B6R�VZ�͟�j�I�?nYn���,�P�b��fz���	+��ѹw��m2~8q�ab,$�F�?Hk��pxʔWbz��V�[�ȁU-fmx�$�;��\@�٪,ن�W�y ����~��`Q���N����r�a�/���3���tr{n�١��������K��U���ݎ3(��IoAk��q�2bX�αR>D���*#���Z3�U��?�|	{uX֣��@��m/��z�~�mwr��n�'���s�V���m#���k+�`��J\����:1���A��A��Π�{�q��9�{�ׂ&�d|w{��t*%G&�~�����Vy�x�,S�o����Q9��P�R8e6lo>,��Md�N*�W{Q)G9��`TҺ	T�gЍU�K:�6O�OJ�ޝ���NFI���#�o�E!!��H��*�!�6V^v(e|*9��^�,2Λ����^�氊P�&��T̞�� ��*݆��X|��Y�2��C'u�� �� z��2��3��}�M��ؓ�#����,�6A�?E����4��C��<0(�q��Q�������C+�����{�||�'r*u=P�J����w(���z�s��p�a2����fl,�GY9�&x$���?ļ�Y8����]��\�*���g���Ek�?��gJy-��aU+�Cn��W�<m,���l��V�)��&�_��E�.��r���~2�|T�se2�Yt�<�:��V����Ć�yK&;����HZ�b�"d��zP�C�1��Q(-XkBVjɸ���{iA�,��������Wx6�Qp�fʂ�������5�Bj�XQ#�y���m�Z
��Ú���9(���K:�6���.�����L�<z`����Aw�?'s2����$�O�k�o��8J�K�bu�׫���?x��]~
g��6�?gx#C��d���&��{�P���9����q��\`�\Y�����V�o�>�������mH3Jf��9\���H������NS��o</��@{R�֚a��]z��3l��;�'����9_��C2����^y�@T�A�x�kw���g1�`nA>r@Z��Mp~j^*�z��������E��I���/yV��      �      x�3�4����� ]      �   �   x���r�0 е��L�3Z����B��"�J(�|�ӳ<�aS���^ф�D'i�ѬV�K�i�t��t�X+�����T�WhB�H��̽T���й��?h�����OؓK��(��MϪɯ�1,T�5�D�n�����ݓ�>F� �Y��7����$��2������i$�E[	�Qd����RS��<���d�Ƚo,��4�"�Oa      �   c   x�3�,-N-rH�M���K��s99�sS9U�UT��"�,,r3+ҍ��Bs����M#����3B"��=B�+M�K�K+*�b���� ��g     