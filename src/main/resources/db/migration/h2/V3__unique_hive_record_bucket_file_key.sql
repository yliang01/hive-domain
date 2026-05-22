CREATE UNIQUE INDEX IF NOT EXISTS uk_hive_record_bucket_file_key
ON hive_record(bucket_name, file_key);
