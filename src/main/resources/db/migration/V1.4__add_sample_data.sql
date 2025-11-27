-- Sample recipes
INSERT INTO recipes (name, instructions, total_calories, tags, meal_time)
VALUES ('PANCAKES PROTEICE CU IAURT, FRUCTE SI SIROP LIGHT',
        'Amestecă toate ingredientele pentru pancakes până obții un aluat omogen. Coace-le într-o tigaie antiaderentă, 2-3 minute pe fiecare parte (foc mic-mediu). Amestecă toppingul separat: cottage cheese + iaurt + sirop (poate fi sirop de curmale sau o lingurita de miere). Așează toppingul peste pancakes și adaugă fructele deasupra (zmeura, affine)',
        600,
        'high-protein,meal-prep',
        'BREAKFAST'),
       ('Orez cu pui', 'Fierbe orezul și prăjește pieptul de pui, apoi amestecă.', 520, 'proteic, echilibrat', 'LUNCH'),
       ('Omletă cu legume', 'Bate ouăle, adaugă broccoli și ulei de măsline.', 340, 'vegetarian', 'DINNER'),
       ('Hrisca cu pui', 'Fierbe hrisca și prăjește pieptul de pui, apoi amestecă.', 520, 'proteic, echilibrat',
        'LUNCH'),
       ('Omletă cu legume', 'Bate ouăle, adaugă broccoli și ulei de măsline.', 340, 'vegetarian', 'DINNER');

-- Sample ingredients
INSERT INTO ingredients_recipe (recipe_id, ingredient_id, quantity)
VALUES (1, 1, 150),
       (1, 3, 100),
       (1, 4, 80),
       (2, 1, 100),
       (2, 3, 50),
       (2, 4, 10),
       (2, 5, 100),
       (3, 6, 80),
       (3, 8, 100);

-- Sample clients
INSERT INTO clients (name, age, weight, height, goal, daily_calories)
VALUES ('Alice Johnson', 28, 60.5, 165, 'weight loss', 1800),
       ('Mark Rivera', 35, 78.2, 178, 'muscle gain', 2500),
       ('Andrei Popescu', 30, 78, 180, 'slăbire', 2000),
       ('Maria Ionescu', 25, 60, 165, 'menținere', 1900),
       ('Mihai Georgescu', 35, 82, 175, 'masă musculară', 2500);

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

INSERT INTO sport_activities (id, name, duration)
VALUES (1, 'Alergare usoara, puls mediu 150 bpm', 30),
       (2, '100 burpees, 5 seturi x 20', 20),
       (3, 'Tabata challenge, 3 runde: flotari, genoflexiuni, sprinturi.', 30);
