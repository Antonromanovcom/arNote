CREATE VIEW wishes_view_not_realized AS
  SELECT
    w.id,
    w.wish,
    w.price,
    w.priority,
    w.priority_group
  FROM arnote.public.wishes AS w WHERE w.user_id = 2 AND (w.realized  = FALSE OR w.realized ISNULL) ;