CREATE TABLE todos (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL DEFAULT '',
    created_at TEXT NOT NULL DEFAULT (date('now'))
);

CREATE TABLE tasks (
    id INTEGER PRIMARY KEY,
    text TEXT NOT NULL,
    todo INTEGER NOT NULL,
    is_active INTEGER NOT NULL DEFAULT 0,
    created_at TEXT NOT NULL DEFAULT (date('now')),
    completed_at TEXT
);

CREATE INDEX tasks_todo_index ON tasks(todo);