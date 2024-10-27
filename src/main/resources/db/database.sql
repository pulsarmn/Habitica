--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0 (Debian 17.0-1.pgdg120+1)
-- Dumped by pg_dump version 17.0 (Debian 17.0-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: task; Type: SCHEMA; Schema: -; Owner: pulsar
--

CREATE SCHEMA task;


ALTER SCHEMA task OWNER TO pulsar;

--
-- Name: users; Type: SCHEMA; Schema: -; Owner: pulsar
--

CREATE SCHEMA users;


ALTER SCHEMA users OWNER TO pulsar;

--
-- Name: set_default_status(); Type: FUNCTION; Schema: public; Owner: pulsar
--

CREATE FUNCTION public.set_default_status() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.status IS NULL THEN
        NEW.status := FALSE;
    END IF;
    RETURN NEW;
END
$$;


ALTER FUNCTION public.set_default_status() OWNER TO pulsar;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: test; Type: TABLE; Schema: public; Owner: pulsar
--

CREATE TABLE public.test (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    status boolean DEFAULT false NOT NULL
);


ALTER TABLE public.test OWNER TO pulsar;

--
-- Name: test_id_seq; Type: SEQUENCE; Schema: public; Owner: pulsar
--

CREATE SEQUENCE public.test_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.test_id_seq OWNER TO pulsar;

--
-- Name: test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pulsar
--

ALTER SEQUENCE public.test_id_seq OWNED BY public.test.id;


--
-- Name: daily_task; Type: TABLE; Schema: task; Owner: pulsar
--

CREATE TABLE task.daily_task (
    id integer NOT NULL,
    heading character varying(100) NOT NULL,
    description character varying(300),
    complexity character varying(30),
    deadline date,
    status boolean DEFAULT false,
    series integer DEFAULT 0 NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE task.daily_task OWNER TO pulsar;

--
-- Name: daily_task_id_seq; Type: SEQUENCE; Schema: task; Owner: pulsar
--

CREATE SEQUENCE task.daily_task_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE task.daily_task_id_seq OWNER TO pulsar;

--
-- Name: daily_task_id_seq; Type: SEQUENCE OWNED BY; Schema: task; Owner: pulsar
--

ALTER SEQUENCE task.daily_task_id_seq OWNED BY task.daily_task.id;


--
-- Name: habit; Type: TABLE; Schema: task; Owner: pulsar
--

CREATE TABLE task.habit (
    id integer NOT NULL,
    heading character varying(100) NOT NULL,
    description character varying(300),
    complexity character varying(30),
    good_series integer DEFAULT 0 NOT NULL,
    bad_series integer DEFAULT 0 NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE task.habit OWNER TO pulsar;

--
-- Name: habit_id_seq; Type: SEQUENCE; Schema: task; Owner: pulsar
--

CREATE SEQUENCE task.habit_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE task.habit_id_seq OWNER TO pulsar;

--
-- Name: habit_id_seq; Type: SEQUENCE OWNED BY; Schema: task; Owner: pulsar
--

ALTER SEQUENCE task.habit_id_seq OWNED BY task.habit.id;


--
-- Name: reward; Type: TABLE; Schema: task; Owner: pulsar
--

CREATE TABLE task.reward (
    id integer NOT NULL,
    heading character varying(100) NOT NULL,
    description character varying(300),
    cost numeric(10,2),
    user_id integer NOT NULL
);


ALTER TABLE task.reward OWNER TO pulsar;

--
-- Name: reward_id_seq; Type: SEQUENCE; Schema: task; Owner: pulsar
--

CREATE SEQUENCE task.reward_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE task.reward_id_seq OWNER TO pulsar;

--
-- Name: reward_id_seq; Type: SEQUENCE OWNED BY; Schema: task; Owner: pulsar
--

ALTER SEQUENCE task.reward_id_seq OWNED BY task.reward.id;


--
-- Name: task; Type: TABLE; Schema: task; Owner: pulsar
--

CREATE TABLE task.task (
    id integer NOT NULL,
    heading character varying(100) NOT NULL,
    description character varying(300),
    complexity character varying(30),
    deadline date,
    status boolean DEFAULT false,
    user_id integer NOT NULL
);


ALTER TABLE task.task OWNER TO pulsar;

--
-- Name: task_id_seq; Type: SEQUENCE; Schema: task; Owner: pulsar
--

CREATE SEQUENCE task.task_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE task.task_id_seq OWNER TO pulsar;

--
-- Name: task_id_seq; Type: SEQUENCE OWNED BY; Schema: task; Owner: pulsar
--

ALTER SEQUENCE task.task_id_seq OWNED BY task.task.id;


--
-- Name: user_balances; Type: TABLE; Schema: users; Owner: pulsar
--

CREATE TABLE users.user_balances (
    user_id integer NOT NULL,
    balance numeric(10,2) DEFAULT 0.00
);


ALTER TABLE users.user_balances OWNER TO pulsar;

--
-- Name: user_images; Type: TABLE; Schema: users; Owner: pulsar
--

CREATE TABLE users.user_images (
    user_id integer NOT NULL,
    image_addr character varying(200)
);


ALTER TABLE users.user_images OWNER TO pulsar;

--
-- Name: user_statistics; Type: TABLE; Schema: users; Owner: pulsar
--

CREATE TABLE users.user_statistics (
    user_id integer NOT NULL,
    total_visits integer DEFAULT 0
);


ALTER TABLE users.user_statistics OWNER TO pulsar;

--
-- Name: users; Type: TABLE; Schema: users; Owner: pulsar
--

CREATE TABLE users.users (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(60) NOT NULL,
    nickname character varying(30) NOT NULL,
    created_at date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE users.users OWNER TO pulsar;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: users; Owner: pulsar
--

CREATE SEQUENCE users.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE users.users_id_seq OWNER TO pulsar;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: users; Owner: pulsar
--

ALTER SEQUENCE users.users_id_seq OWNED BY users.users.id;


--
-- Name: test id; Type: DEFAULT; Schema: public; Owner: pulsar
--

ALTER TABLE ONLY public.test ALTER COLUMN id SET DEFAULT nextval('public.test_id_seq'::regclass);


--
-- Name: daily_task id; Type: DEFAULT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.daily_task ALTER COLUMN id SET DEFAULT nextval('task.daily_task_id_seq'::regclass);


--
-- Name: habit id; Type: DEFAULT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.habit ALTER COLUMN id SET DEFAULT nextval('task.habit_id_seq'::regclass);


--
-- Name: reward id; Type: DEFAULT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.reward ALTER COLUMN id SET DEFAULT nextval('task.reward_id_seq'::regclass);


--
-- Name: task id; Type: DEFAULT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.task ALTER COLUMN id SET DEFAULT nextval('task.task_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.users ALTER COLUMN id SET DEFAULT nextval('users.users_id_seq'::regclass);


--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: pulsar
--

COPY public.test (id, name, status) FROM stdin;
5	NAME	f
6	NAME	t
\.


--
-- Name: test_id_seq; Type: SEQUENCE SET; Schema: public; Owner: pulsar
--

SELECT pg_catalog.setval('public.test_id_seq', 6, true);


--
-- Name: daily_task_id_seq; Type: SEQUENCE SET; Schema: task; Owner: pulsar
--

SELECT pg_catalog.setval('task.daily_task_id_seq', 67, true);


--
-- Name: habit_id_seq; Type: SEQUENCE SET; Schema: task; Owner: pulsar
--

SELECT pg_catalog.setval('task.habit_id_seq', 28, true);


--
-- Name: reward_id_seq; Type: SEQUENCE SET; Schema: task; Owner: pulsar
--

SELECT pg_catalog.setval('task.reward_id_seq', 21, true);


--
-- Name: task_id_seq; Type: SEQUENCE SET; Schema: task; Owner: pulsar
--

SELECT pg_catalog.setval('task.task_id_seq', 121, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: users; Owner: pulsar
--

SELECT pg_catalog.setval('users.users_id_seq', 14, true);


--
-- Name: test test_pkey; Type: CONSTRAINT; Schema: public; Owner: pulsar
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);


--
-- Name: daily_task daily_task_pkey; Type: CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.daily_task
    ADD CONSTRAINT daily_task_pkey PRIMARY KEY (id);


--
-- Name: habit habit_pkey; Type: CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.habit
    ADD CONSTRAINT habit_pkey PRIMARY KEY (id);


--
-- Name: reward reward_pkey; Type: CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.reward
    ADD CONSTRAINT reward_pkey PRIMARY KEY (id);


--
-- Name: task task_pkey; Type: CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.task
    ADD CONSTRAINT task_pkey PRIMARY KEY (id);


--
-- Name: user_balances user_balances_pkey; Type: CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.user_balances
    ADD CONSTRAINT user_balances_pkey PRIMARY KEY (user_id);


--
-- Name: user_images user_images_pkey; Type: CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.user_images
    ADD CONSTRAINT user_images_pkey PRIMARY KEY (user_id);


--
-- Name: user_statistics user_statistics_pkey; Type: CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.user_statistics
    ADD CONSTRAINT user_statistics_pkey PRIMARY KEY (user_id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_nickname_key; Type: CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.users
    ADD CONSTRAINT users_nickname_key UNIQUE (nickname);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: test before_insert_test; Type: TRIGGER; Schema: public; Owner: pulsar
--

CREATE TRIGGER before_insert_test BEFORE INSERT ON public.test FOR EACH ROW EXECUTE FUNCTION public.set_default_status();


--
-- Name: task before_insert_task; Type: TRIGGER; Schema: task; Owner: pulsar
--

CREATE TRIGGER before_insert_task BEFORE INSERT ON task.task FOR EACH ROW EXECUTE FUNCTION public.set_default_status();


--
-- Name: daily_task daily_task_user_id_fkey; Type: FK CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.daily_task
    ADD CONSTRAINT daily_task_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id);


--
-- Name: habit habit_user_id_fkey; Type: FK CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.habit
    ADD CONSTRAINT habit_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id);


--
-- Name: reward reward_user_id_fkey; Type: FK CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.reward
    ADD CONSTRAINT reward_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id);


--
-- Name: task task_user_id_fkey; Type: FK CONSTRAINT; Schema: task; Owner: pulsar
--

ALTER TABLE ONLY task.task
    ADD CONSTRAINT task_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id);


--
-- Name: user_balances user_balances_user_id_fkey; Type: FK CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.user_balances
    ADD CONSTRAINT user_balances_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id) ON DELETE CASCADE;


--
-- Name: user_images user_images_user_id_fkey; Type: FK CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.user_images
    ADD CONSTRAINT user_images_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id) ON DELETE CASCADE;


--
-- Name: user_statistics user_statistics_user_id_fkey; Type: FK CONSTRAINT; Schema: users; Owner: pulsar
--

ALTER TABLE ONLY users.user_statistics
    ADD CONSTRAINT user_statistics_user_id_fkey FOREIGN KEY (user_id) REFERENCES users.users(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

