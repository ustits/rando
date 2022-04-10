CREATE TABLE todos (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE tasks (
    value TEXT NOT NULL,
    todo INTEGER NOT NULL,
    FOREIGN KEY (todo) REFERENCES todos(id)
);

CREATE INDEX tasksindex ON tasks(todo);