USE poseiden_trading_test;

INSERT INTO Users (fullname, username, password, role) 
VALUES 
  ("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN"),
  ("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");

INSERT INTO bidlist (BidListId, account,  type)
VALUES 
  (1, "TestAccount", "TestType");

INSERT INTO curvepoint (Id, term)
VALUES 
  (1, 1.0);