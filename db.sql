DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS comment_likes;
DROP TABLE IF EXISTS post_bookmarks;
DROP TABLE IF EXISTS post_likes;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS post_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS post_images;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(512),                            -- 图片网址
    bio TEXT,                                           -- 个人简介
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()       -- 创建时间
);
CREATE INDEX idx_users_email ON users(email);   
-- SELECT id, password_hash FROM users WHERE email = 'user@example.com';
-- 主要用于登录的时候
-- token 会负责其它的东西


CREATE TABLE posts (
    id SERIAL PRIMARY KEY,                      -- 生成唯一的id
    user_id INT,                                -- 用户id  记录这个帖子是谁
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,                      -- 正文内容
    view_count INT NOT NULL DEFAULT 0,          -- 帖子的浏览数、点赞数、评论数 和 收藏数
    like_count INT NOT NULL DEFAULT 0,
    comment_count INT NOT NULL DEFAULT 0,
    bookmark_count INT NOT NULL DEFAULT 0,
    is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,    -- 是不是匿名发帖子 可以匿名
    status VARCHAR(20) NOT NULL DEFAULT 'published' CHECK (status IN ('published', 'draft', 'hidden_by_admin')),
    -- 'published', 'draft', 'hidden_by_admin' 只能是这三种形态
    -- 
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    -- NOW() 是一个数据库函数，返回当前的事务时间 刚插入的时候 

    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL
    -- fk_user 约束名字
);

CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);
-- 加载首页信息流、频道列表等


CREATE OR REPLACE FUNCTION trigger_set_timestamp()
-- CREATE OR REPLACE 创建或者代替 一个 函数
RETURNS TRIGGER AS $$
-- RETURNS TRIGGER 专门给 触发器使用的
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
-- AS $$ ... $$ 这个是定义函数体的方式
-- NEW 代表着即将被写入数据库的那一行数据
$$ LANGUAGE plpgsql;
-- LANGUAGE plpgsql 告诉编译器 这个函数是用 plpgsql 语言编写的，这是PostgreSQL的标准化过程语言

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON posts
-- 这是触发器的时机和事件
-- 触发事件是更新操作
-- 触发器是绑定在 posts 表
FOR EACH ROW
-- 指定触发器的粒度
-- updated_at
EXECUTE PROCEDURE trigger_set_timestamp();


CREATE TABLE post_images (
    id SERIAL PRIMARY KEY,
    -- 自增的、唯一的、非空的主键
    post_id INT NOT NULL,
    image_url TEXT NOT NULL,
    display_order SMALLINT NOT NULL DEFAULT 0,
    -- 
    CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES posts(id) ON DELETE CASCADE
    -- 帖子 被删除了 这个也没了
);
CREATE INDEX idx_post_images_post_id ON post_images(post_id);


CREATE TABLE tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);


CREATE TABLE post_tags (
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_tag FOREIGN KEY(tag_id) REFERENCES tags(id) ON DELETE CASCADE
);


CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT,
    content TEXT NOT NULL,
    parent_comment_id INT,
    like_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_parent_comment FOREIGN KEY(parent_comment_id) REFERENCES comments(id) ON DELETE CASCADE
);
CREATE INDEX idx_comments_post_id_created_at ON comments(post_id, created_at ASC);


CREATE TABLE post_likes (
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, post_id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES posts(id) ON DELETE CASCADE
);


CREATE TABLE post_bookmarks (
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, post_id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_post FOREIGN KEY(post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE comment_likes (
    user_id INT NOT NULL,
    -- 点赞用户的id
    comment_id INT NOT NULL,
    -- 评论的id
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, comment_id),

    -- 约束:
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment FOREIGN KEY(comment_id) REFERENCES comments(id) ON DELETE CASCADE
);



CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    recipient_user_id INT NOT NULL,
    actor_user_id INT,
    action_type VARCHAR(50) NOT NULL,

    source_id INT NOT NULL,
    -- source_id：源ID
    -- 如果 action_type 是 'like_post'，那么 source_id 就是被点赞的帖子的 post_id
    -- 如果 action_type 是 'comment_on_post'，那么 source_id 就是那条新评论的 comment_id
    -- 这样，当用户点击这条通知时，前端就知道应该跳转到哪个帖子或哪条评论的页面
    -- 这样设计会好很多啊 

    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    -- 是否已读

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),


    CONSTRAINT fk_recipient_user FOREIGN KEY(recipient_user_id) REFERENCES users(id) ON DELETE CASCADE,
    -- 对应通知者的id 加一个 管理员界面
    CONSTRAINT fk_actor_user FOREIGN KEY(actor_user_id) REFERENCES users(id) ON DELETE SET NULL
);
CREATE INDEX idx_notifications_recipient_id ON notifications(recipient_user_id);
