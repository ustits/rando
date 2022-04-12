CREATE TABLE tasks_v2 (
    id INTEGER PRIMARY KEY,
    text TEXT NOT NULL,
    completed INTEGER NOT NULL DEFAULT 0,
    todo INTEGER NOT NULL,
    UNIQUE(id, todo),
    FOREIGN KEY (todo) REFERENCES todos(id) ON DELETE CASCADE ON UPDATE NO ACTION
);

INSERT INTO tasks_v2 (text, todo)
SELECT value, todo
FROM tasks;

DROP INDEX tasksindex;
DROP TABLE tasks;
ALTER TABLE tasks_v2 RENAME to tasks;

CREATE INDEX tasks_index ON tasks(todo);

CREATE TABLE todos_active_task (
    todo INTEGER PRIMARY KEY,
    task INTEGER NOT NULL,
    FOREIGN KEY (task, todo) REFERENCES tasks(id, todo) ON DELETE CASCADE ON UPDATE NO ACTION
);
