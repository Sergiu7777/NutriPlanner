-- =============================================================
-- TABLE: foods
-- =============================================================
CREATE TABLE IF NOT EXISTS foods
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT NOT NULL UNIQUE,
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
    tags           TEXT           -- comma-separated or JSON list of tags
);

-- =============================================================
-- TABLE: ingredients_recipe
-- Mapping table for recipes and foods (many-to-many)
-- =============================================================
CREATE TABLE IF NOT EXISTS ingredients_recipe
(
    id_recipe INTEGER NOT NULL,
    id_food   INTEGER NOT NULL,
    quantity  REAL    NOT NULL CHECK (quantity > 0), -- in grams or units
    PRIMARY KEY (id_recipe, id_food),
    FOREIGN KEY (id_recipe) REFERENCES recipes (id) ON DELETE CASCADE,
    FOREIGN KEY (id_food) REFERENCES foods (id) ON DELETE CASCADE
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
-- TABLE: plans
-- Stores meal plans associated with each client
-- =============================================================
CREATE TABLE IF NOT EXISTS plans
(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    id_client      INTEGER NOT NULL,
    date_created   TEXT    NOT NULL DEFAULT (datetime('now')),
    total_calories REAL CHECK (total_calories >= 0),
    FOREIGN KEY (id_client) REFERENCES clients (id) ON DELETE CASCADE
);

-- =============================================================
-- TABLE: plan_recipes
-- Links meal plans with recipes, defining meal time (e.g., breakfast, lunch)
-- =============================================================
CREATE TABLE IF NOT EXISTS plan_recipes
(
    id_plan   INTEGER NOT NULL,
    id_recipe INTEGER NOT NULL,
    day       INTEGER NOT NULL,
    meal_time TEXT    NOT NULL CHECK (meal_time IN ('breakfast', 'lunch', 'dinner', 'snack')),
    PRIMARY KEY (id_plan, id_recipe, meal_time),
    FOREIGN KEY (id_plan) REFERENCES plans (id) ON DELETE CASCADE,
    FOREIGN KEY (id_recipe) REFERENCES recipes (id) ON DELETE CASCADE
);

-- =============================================================
-- INDEXES (for faster lookups)
-- =============================================================
CREATE INDEX IF NOT EXISTS idx_clients_name ON clients (name);
CREATE INDEX IF NOT EXISTS idx_plans_client ON plans (id_client);
CREATE INDEX IF NOT EXISTS idx_plan_recipes_plan ON plan_recipes (id_plan);
CREATE INDEX IF NOT EXISTS idx_plan_recipes_recipe ON plan_recipes (id_recipe);
CREATE INDEX IF NOT EXISTS idx_foods_category ON foods (category);
CREATE INDEX IF NOT EXISTS idx_recipes_name ON recipes (name);
CREATE INDEX IF NOT EXISTS idx_ingredients_recipe_recipe ON ingredients_recipe (id_recipe);
CREATE INDEX IF NOT EXISTS idx_ingredients_recipe_food ON ingredients_recipe (id_food);
