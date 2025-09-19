CREATE OR REPLACE FUNCTION ST_CS4ObserverPropertiesToJson(data_id integer)
RETURNS jsonb AS
$BODY$
      SELECT jsonb_build_object(
	  'observed_properties', jsonb_agg(property)
	  )
      FROM (
	  SELECT to_jsonb(row) as property
      FROM (SELECT o.name, c.observed_property_count FROM public.cs4playas_data_observed_property_count as c join cs4playas_observed_property as o on c.observed_property_count_key = o.id where c.cs4playas_data_id = $1) row) properties;
$BODY$
LANGUAGE SQL;

CREATE OR REPLACE FUNCTION ST_CS4DataToGeoJson()
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
        'geometry',   ST_AsGeoJSON(ST_Multi(ST_Union(initial_coordinates, final_coordinates)))::jsonb,
        'properties', to_jsonb(row) || to_jsonb(ST_CS4ObserverPropertiesToJson(row.id))
      ) AS feature
      FROM (SELECT * FROM public.cs4playas_data) row) features;
$BODY$
LANGUAGE SQL;