-- =============================================================
-- TABLE: day_plan
-- =============================================================
CREATE TABLE IF NOT EXISTS day_plan
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    day     INTEGER NOT NULL,
    note    TEXT,
    plan_id INTEGER NOT NULL,
    CONSTRAINT fk_dayplan_plan FOREIGN KEY (plan_id) REFERENCES plans (id) ON DELETE CASCADE
);

-- =============================================================
-- TABLE: day_plan_recipe
-- Many-to-many relation between day_plan and recipe, with meal_time
-- =============================================================
CREATE TABLE IF NOT EXISTS day_plan_recipe
(
    day_plan_id INTEGER     NOT NULL,
    recipe_id   INTEGER     NOT NULL,
    meal_time   VARCHAR(50) NOT NULL, -- stores ENUM values: BREAKFAST, LUNCH, DINNER, SNACK
    PRIMARY KEY (day_plan_id, recipe_id),
    CONSTRAINT fk_dpr_dayplan FOREIGN KEY (day_plan_id) REFERENCES day_plan (id) ON DELETE CASCADE,
    CONSTRAINT fk_dpr_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);

-- =============================================================
-- TABLE: day_plan_activity
-- Many-to-many relation between day_plan and plan_activities
-- =============================================================
CREATE TABLE IF NOT EXISTS day_plan_activity
(
    day_plan_id INTEGER NOT NULL,
    activity_id INTEGER NOT NULL,
    PRIMARY KEY (day_plan_id, activity_id),
    CONSTRAINT fk_dpa_dayplan FOREIGN KEY (day_plan_id) REFERENCES day_plan (id) ON DELETE CASCADE,
    CONSTRAINT fk_dpa_activity FOREIGN KEY (activity_id) REFERENCES plan_activities (id) ON DELETE CASCADE
);

-- =============================================================
-- OPTIONAL INDEXES
-- =============================================================
CREATE INDEX IF NOT EXISTS idx_day_plan_plan_id ON day_plan (plan_id);
CREATE INDEX IF NOT EXISTS idx_dpr_mealtime ON day_plan_recipe (meal_time);
CREATE INDEX IF NOT EXISTS idx_dpr_recipe ON day_plan_recipe (recipe_id);
CREATE INDEX IF NOT EXISTS idx_dpa_activity ON day_plan_activity (activity_id);
