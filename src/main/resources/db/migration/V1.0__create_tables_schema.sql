-- =============================================================
-- TABLE: plans
-- Stores meal plans associated with each client
-- =============================================================
CREATE TABLE IF NOT EXISTS plans
(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    title          TEXT    NOT NULL,
    total_calories REAL CHECK (total_calories > 0),
    description    TEXT    NOT NULL,
    number_of_days INTEGER CHECK (number_of_days > 0),
    date_created   TEXT    NOT NULL DEFAULT (datetime('now')),
    client_id      INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

-- =============================================================
-- TABLE: ingredients
-- =============================================================
CREATE TABLE IF NOT EXISTS ingredients
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT NOT NULL,
    calories REAL NOT NULL, -- kcal per 100g
    protein  REAL NOT NULL, -- grams per 100g
    carbs    REAL NOT NULL, -- grams per 100g
    fat      REAL NOT NULL, -- grams per 100g
    category TEXT,          -- e.g., "Vegetable", "Fruit", "Protein"
    tags     TEXT           -- comma-separated or JSON list of tags
);

-- =============================================================
-- TABLE: recipes
-- =============================================================
CREATE TABLE IF NOT EXISTS recipes
(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    name           TEXT NOT NULL UNIQUE,
    instructions   TEXT NOT NULL, -- full recipe instructions
    total_calories REAL NOT NULL, -- computed total calories
    tags           TEXT,          -- comma-separated or JSON list of tags
    meal_time      TEXT           -- defining meal time (e.g., breakfast, lunch)
);

-- =============================================================
-- TABLE: ingredients_recipe
-- Mapping table for recipes and foods (many-to-many)
-- =============================================================
CREATE TABLE IF NOT EXISTS ingredients_recipe
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    recipe_id     INTEGER NOT NULL,
    ingredient_id INTEGER NOT NULL,
    quantity      REAL    NOT NULL CHECK (quantity > 0), -- in grams or units
    FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id) ON DELETE CASCADE
);

-- =============================================================
-- TABLE: clients
-- Stores client information and personal details
-- =============================================================
CREATE TABLE IF NOT EXISTS clients
(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    name           TEXT NOT NULL,
    age            INTEGER CHECK (age >= 0),
    weight         REAL CHECK (weight > 0),        -- in kilograms
    height         REAL CHECK (height > 0),        -- in centimeters
    goal           TEXT,                           -- e.g., "weight loss", "muscle gain"
    daily_calories REAL CHECK (daily_calories > 0) -- target kcal/day
);

-- =============================================================
-- TABLE: plan_recipes
-- Links meal plans with recipes, defining meal time (e.g., breakfast, lunch)
-- =============================================================
CREATE TABLE IF NOT EXISTS plan_recipes
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    plan_id   INTEGER NOT NULL,
    recipe_id INTEGER NOT NULL,
    day       INTEGER NOT NULL,
    meal_time TEXT    NOT NULL CHECK (meal_time IN ('breakfast', 'lunch', 'dinner', 'snack')),
    FOREIGN KEY (plan_id) REFERENCES plans (id) ON DELETE CASCADE,
    FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);

-- =============================================================
-- TABLE: plan_activities
-- Links meal plans with daily sport activity
-- =============================================================
CREATE TABLE IF NOT EXISTS plan_activities
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    activity TEXT NOT NULL
);

-- =============================================================
-- INDEXES (for faster lookups)
-- =============================================================
-- CREATE INDEX IF NOT EXISTS idx_clients_name ON clients (name);
-- CREATE INDEX IF NOT EXISTS idx_plans_client ON plans (id_client);
-- CREATE INDEX IF NOT EXISTS idx_plan_recipes_plan ON plan_recipes (id_plan);
-- CREATE INDEX IF NOT EXISTS idx_plan_recipes_recipe ON plan_recipes (id_recipe);
-- CREATE INDEX IF NOT EXISTS idx_foods_category ON ingredients (category);
-- CREATE INDEX IF NOT EXISTS idx_recipes_name ON recipes (name);
-- CREATE INDEX IF NOT EXISTS idx_ingredients_recipe_recipe ON ingredients_recipe (id_recipe);
-- CREATE INDEX IF NOT EXISTS idx_ingredients_recipe_food ON ingredients_recipe (id_food);
