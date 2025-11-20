-- Sample recipes
INSERT INTO recipes (name, instructions, total_calories, tags)
VALUES ('PANCAKES PROTEICE CU IAURT, FRUCTE SI SIROP LIGHT',
        'Amestecă toate ingredientele pentru pancakes până obții un aluat omogen. Coace-le într-o tigaie antiaderentă, 2-3 minute pe fiecare parte (foc mic-mediu). Amestecă toppingul separat: cottage cheese + iaurt + sirop (poate fi sirop de curmale sau o lingurita de miere). Așează toppingul peste pancakes și adaugă fructele deasupra (zmeura, affine)',
        600,
        'high-protein,meal-prep');

-- Sample ingredients
INSERT INTO ingredients_recipe (recipe_id, ingredient_id, quantity)
VALUES (1, 1, 150),
       (1, 3, 100),
       (1, 4, 80);

-- Sample clients
INSERT INTO clients (name, age, weight, height, goal, daily_calories)
VALUES ('Alice Johnson', 28, 60.5, 165, 'weight loss', 1800),
       ('Mark Rivera', 35, 78.2, 178, 'muscle gain', 2500);

-- Sample meal plans
INSERT INTO plans (id, title, total_calories, description, number_of_days, date_created, client_id)
VALUES (1, "Plan test 1", 1750, "Un plan 1", 7, datetime('now'), 1),
       (2, "Plan test 2", 2450, "Un plan 2", 14, datetime('now'), 2);

-- Example recipes from previous migration assumed:
-- Recipe IDs correspond to entries in the `recipes` table

-- Link plans to recipes
INSERT INTO plan_recipes (plan_id, recipe_id, day, meal_time)
VALUES (1, 1, 1, 'breakfast'),
       (1, 1, 2, 'lunch'),
       (2, 1, 3, 'dinner');



-- ------------------------------------------------------------
-- 1. Tabel: foods (alimente)
-- ------------------------------------------------------------
-- | id | nume aliment | calorii | proteine | carbohidrati | grasimi | categorie | etichete |
-- |----|------------------|----------|-----------|---------------|----------|-------------|----------|
-- | 1 | Orez brun | 360 | 7.5 | 74 | 2.5 | cereale | vegan,
-- fara-gluten |
-- | 2 | Piept de pui | 165 | 31 | 0 | 3.5 | carne | slab, proteic |
-- | 3 | Broccoli | 34 | 3 | 7 | 0.4 | legume | vegan, low-carb |
-- | 4 | Ulei de măsline | 884 | 0 | 0 | 100 | grăsimi | vegan, keto |
-- | 5 | Ou întreg | 155 | 13 | 1.1 | 11 | lactate | vegetarian |
-- Exemplu SQL:
-- INSERT INTO foods (nume, calorii, proteine, carbohidrati, grasimi, categorie, tags)
-- VALUES
--     ('Orez brun', 360, 7.5, 74, 2.5, 'cereale', 'vegan, fara-gluten'),
--     ('Piept de pui', 165, 31, 0, 3.5, 'carne', 'slab, proteic'),
--     ('Broccoli', 34, 3, 7, 0.4, 'legume', 'vegan, low-carb'),
--     ('Ulei de masline', 884, 0, 0, 100, 'grasimi', 'vegan, keto'),
--     ('Ou intreg', 155, 13, 1.1, 11, 'lactate', 'vegetarian');
-- ------------------------------------------------------------
-- 2. Tabel: rețete (recipes)
-- ------------------------------------------------------------
-- | id | nume | instrucțiuni | calorii_totale | etichete |
-- |----|------|---------------|----------------|-----------|
-- | 1 | Orez cu pui | Fierbe orezul și prăjește pieptul de pui, apoi amestecă. | 520 |
-- proteic, echilibrat |
-- | 2 | Omletă cu legume | Bate ouăle, adaugă broccoli și ulei de măsline. | 340 |
-- vegetarian |
-- | 3 | Fulgi de ovăz cu fructe | Fierbe ovăzul și adaugă mere feliate. | 300 | vegan,
-- mic-dejun |
-- ------------------------------------------------------------
-- 3. Tabel: ingrediente_rețetă
-- ------------------------------------------------------------
-- | id_reteta | id_aliment | cantitate (g) |
-- |------------|-------------|---------------|
-- | 1 | 1 (Orez brun) | 100 |
-- | 1 | 2 (Piept de pui) | 150 |
-- | 2 | 3 (Broccoli) | 50 |
-- | 2 | 4 (Ulei de măsline) | 10 |
-- | 2 | 5 (Ou întreg) | 100 |
-- | 3 | 6 (Fulgi de ovăz) | 80 |
-- | 3 | 8 (Măr) | 100 |
-- ------------------------------------------------------------
-- 4. Tabel: clienți (clients)
-- ------------------------------------------------------------
-- | id | nume | vârstă | greutate | înălțime | obiectiv | calorii_zilnice |
-- |----|------|---------|-----------|-----------|-----------|----------------|
-- | 1 | Andrei Popescu | 30 | 78 | 180 | slăbire | 2000 |
-- | 2 | Maria Ionescu | 25 | 60 | 165 | menținere | 1900 |
-- | 3 | Mihai Georgescu | 35 | 82 | 175 | masă musculară | 2500 |
-- ------------------------------------------------------------
-- 5. Tabel: planuri (plans)
-- ------------------------------------------------------------
-- | id | id_client | data_creare | calorii_totale |
-- |----|------------|---------------|----------------|
-- | 1 | 1 | 2025-11-12 | 1950 |
-- | 2 | 2 | 2025-11-12 | 1900 |
-- ------------------------------------------------------------
-- 6. Tabel: plan_rețete (plan_recipes)
-- ------------------------------------------------------------
-- | id_plan | id_reteta | momentul_zilei |
-- |----------|------------|----------------|
-- | 1 | 3 (Fulgi de ovăz cu fructe) | mic_dejun |
-- | 1 | 1 (Orez cu pui) | prânz |
-- | 1 | 2 (Omletă cu legume) | cină |
-- | 2 | 3 (Fulgi de ovăz cu fructe) | mic_dejun |
-- | 2 | 2 (Omletă cu legume) | prânz |
-- ------------------------------------------------------------