INSERT INTO Template (code, description, path)
VALUES ('template_code', 'template_description', 'template_path');

--insertion in table of  template-param
INSERT INTO template_param (id, name, description, type, selector_type, selector, source)
VALUES
    (1, 'client_fname', 'This is a sample parameter', 'java.lang.String', 'JSON' ,'input/param1', '/path/to/param1'),
    (2, 'client_lname', 'This is a sample parameter', 'java.lang.String', 'JSON', 'input/param2', '/path/to/param2'),
    (3, 'account', 'This is a sample parameter', 'java.lang.String', 'JSON', 'input/param3', '/path/to/param3'),
    (4, 'agency', 'This is a sample parameter', 'java.lang.String', 'JSON','input/param4', '/path/to/param4'),
    (5, 'transactions', 'This is a sample parameter', 'ArrayList', 'JSON','input/param5', '/path/to/param5'),
    (6, 'openingDate', 'This is a sample parameter', 'java.lang.String', 'JSON','input/param6', '/path/to/param6');
