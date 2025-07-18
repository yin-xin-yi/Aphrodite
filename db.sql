
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id SERIAL PRIMARY KEY,                          -- 用户ID，自增主键，最简单高效
    username VARCHAR(50) UNIQUE NOT NULL,           -- 登录用户名，唯一且不能为空
    nickname VARCHAR(50) NOT NULL,                  -- 用户昵称，用于显示
    email VARCHAR(255) UNIQUE NOT NULL,             -- 电子邮箱，用于注册验证和找回密码，唯一
    password_hash VARCHAR(255) NOT NULL,            -- 【安全重点】存储加密后的密码哈希，绝不存明文！
    avatar_url VARCHAR(512),                        -- 头像图片的URL，可以为空
    bio TEXT,                                       -- 个人简介，可以为空
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()   -- 注册时间，使用带时区的时间戳，默认为当前时间
);
CREATE INDEX idx_users_email ON users(email);


CREATE TABLE posts (
    id SERIAL PRIMARY KEY,                          -- 帖子ID，自增主键
    user_id INT,                                    -- 作者的用户ID (允许为NULL)
    title VARCHAR(255) NOT NULL,                    -- 帖子标题，不能为空
    content TEXT NOT NULL,                          -- 帖子正文，不能为空
    view_count INT NOT NULL DEFAULT 0,              -- 浏览量，默认为0
    comment_count INT NOT NULL DEFAULT 0,           -- TODO: 评论数，默认为0，方便列表页展示
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),   -- 发布时间
    updated_at TIMESTAMPTZ,                          -- TODO: 最后编辑时间，可以为空
    CONSTRAINT fk_user
        FOREIGN KEY(user_id) 
        REFERENCES users(id)
        ON DELETE SET NULL
);
CREATE INDEX idx_posts_user_id ON posts(user_id);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,                          -- 评论ID，自增主键
    post_id INT NOT NULL,                           -- 所属帖子的ID
    user_id INT,                                    -- 评论者的用户ID (允许为NULL)
    content TEXT NOT NULL,                          -- 评论内容
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),   -- 评论时间
    CONSTRAINT fk_post
        FOREIGN KEY(post_id)
        REFERENCES posts(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);

