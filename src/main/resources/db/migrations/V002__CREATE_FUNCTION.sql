CREATE OR REPLACE FUNCTION ST_TableToGeoJson()
RETURNS jsonb AS
$BODY$
    SELECT jsonb_build_object(
        'type',     'FeatureCollection',
        'features', jsonb_agg(feature)
    )
    FROM (
      SELECT jsonb_build_object(
        'type',       'Feature',
        'id',         row.id,
        'geometry',   ST_AsGeoJSON(location)::jsonb,
        'properties', to_jsonb(row) - 'gid' - 'geom'
      ) AS feature
      FROM (SELECT * FROM public.data) row) features;
$BODY$
LANGUAGE SQL