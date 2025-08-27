INSERT INTO users (name, email, password)
SELECT 
  initcap(first_name || ' ' || last_name) AS full_name,
  lower(first_name || '.' || last_name || i || '@example.com') AS email,
  crypt('user123', gen_salt('bf')) AS password
FROM (
  SELECT i,
         (ARRAY['andi','budi','citra','dedi','eka','fajar','gita','hadi','indra','joko'])[ (random()*9+1)::int ] AS first_name,
         (ARRAY['saputra','wijaya','putri','maulana','siregar','pratama','permata','susanto','rahman','syahputra'])[ (random()*9+1)::int ] AS last_name
  FROM generate_series(1,20) s(i)
) t;
