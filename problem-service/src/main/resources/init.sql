CREATE DATABASE IF NOT EXISTS mockcote_problem CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mockcote_problem;

CREATE TABLE problem (
    problem_id INT PRIMARY KEY,         -- 문제 ID
    title VARCHAR(255),                 -- 문제 제목
    difficulty INT NOT NULL,            -- 난이도
    acceptable_user_count INT NOT NULL  -- 맞은 사람 수
);

-- ALTER TABLE problem
-- ADD INDEX idx_difficulty_acceptable_user_count (difficulty, acceptable_user_count);

-- ALTER TABLE problem DROP INDEX idx_difficulty_acceptable_user_count;

CREATE TABLE tag_label (
    tag_id INT PRIMARY KEY,             -- 태그 식별 번호
    tag_name VARCHAR(100) NOT NULL      -- 태그 이름
);

CREATE TABLE problem_tag (
    problem_id INT NOT NULL,            -- 문제 ID
    tag_id INT NOT NULL,                -- 태그 식별 번호
    PRIMARY KEY (problem_id, tag_id),   -- 문제와 태그 간의 복합 Primary Key
    FOREIGN KEY (problem_id) REFERENCES problem(problem_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag_label(tag_id) ON DELETE CASCADE
);

CREATE TABLE user_solved_problem (
    user_id VARCHAR(50) NOT NULL,
    problem_id INT NOT NULL,
    PRIMARY KEY (user_id, problem_id),
    FOREIGN KEY (problem_id) REFERENCES problem(problem_id)
);
