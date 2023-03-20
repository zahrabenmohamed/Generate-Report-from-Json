INSERT INTO template (id,code, description, path)
VALUES (1,'facture', 'this is an invoice', 'facture.jrxml'),
       (2,'RIB','This is a RIB ','RIB.jrxml'),
       (3,'releve','This is a releve','releve.jrxml');


--insertion in table of  template-param
INSERT INTO template_param (id, name, description, type, selector_type, selector, source)
VALUES
    (1, 'client_fname', 'This is a sample parameter', 'java.lang.String', 'JSON' ,'${name}', 'customer'),
    (2, 'client_lname', 'This is a sample parameter', 'java.lang.String', 'JSON', '${name}', 'customer'),
    (3, 'account', 'This is a sample parameter', 'java.lang.String', 'JSON', 'input/param3', 'customer'),
    (4, 'agency', 'This is a sample parameter', 'java.lang.String', 'JSON','input/param4', 'customer'),
    (5, 'transactions', 'This is a sample parameter', 'ArrayList', 'JSON','${data.id}', 'transactions'),
    (6, 'openingDate', 'This is a sample parameter', 'java.lang.String', 'JSON','input/param6', 'customer'),

INSERT INTO template_template_param(template_id,template_param_id) VALUES(3,2),
                                                                         (3,3),
                                                                         (3,4),
                                                                         (3,5),
                                                                         (3,6);


