MERGE INTO file_category (code, name, description, bucket_name, storage_class, preview_policy, ui_variant, is_system, sort_order, enabled)
KEY(code)
VALUES
  ('IMAGE_PREVIEW', '图片与小文件', '优先展示预览能力', 'hive-image-preview', 'STANDARD', 'IMAGE_FIRST', 'image', TRUE, 10, TRUE),
  ('HOT_FILE', '高频普通文件', '高频访问与检索效率优先', 'hive-hot-file', 'STANDARD', 'DEFAULT', 'hot', TRUE, 20, TRUE),
  ('COLD_ARCHIVE', '低频归档大文件', '低频访问，强调解冻流程', 'hive-cold-archive', 'ARCHIVE', 'NO_PREVIEW', 'archive', TRUE, 30, TRUE);

INSERT INTO file_group (category_id, group_code, group_name, group_desc, sort_order, enabled)
SELECT c.id, 'DEFAULT', '默认分组', '自动创建默认分组', 10, TRUE
FROM file_category c
WHERE c.code = 'IMAGE_PREVIEW'
  AND NOT EXISTS (
    SELECT 1 FROM file_group g WHERE g.category_id = c.id AND g.group_code = 'DEFAULT'
  );
