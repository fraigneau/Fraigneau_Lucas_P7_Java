USE poseiden_trading;

  INSERT INTO Users (fullname, username, password, role)
  VALUES
    ('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN'),
    ('User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');

  INSERT INTO bidlist (account, type, bidQuantity, askQuantity, bid, ask, benchmark, bidListDate, commentary, security, status, trader, book, creationName, creationDate, revisionName, revisionDate, dealName, dealType, sourceListId, side)
  VALUES
      ('account1', 'type1',  1.0, 1.0, 1.0, 1.0, 'benchmark1', '2020-01-01', 'commentary1', 'security1', 'status1', 'trader1', 'book1', 'creationName1', '2020-01-01', 'revisionName1', '2020-01-01', 'dealName1', 'dealType1', 'sourceListId1', 'side1'),
      ('account2', 'type2', 2.0, 2.0, 2.0, 2.0, 'benchmark2', '2020-01-02', 'commentary2', 'security2', 'status2', 'trader2', 'book2', 'creationName2', '2020-01-02', 'revisionName2', '2020-01-02', 'dealName2', 'dealType2', 'sourceListId2', 'side2');
