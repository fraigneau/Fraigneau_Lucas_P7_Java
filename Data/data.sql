USE poseiden_trading;

-- Sample Insert Data for Users
INSERT INTO Users (fullname, username, password, role)
VALUES
  ('Administrator', 'admin', '$2y$10$olgm//nu2c3Wo6X6g/xFUeYsU.ODcYXtmOrf8C/VpvS96Ndv1YOU6', 'ADMIN'),
  ('User', 'user', '$2y$10$olgm//nu2c3Wo6X6g/xFUeYsU.ODcYXtmOrf8C/VpvS96Ndv1YOU6', 'USER');

-- Sample Insert Data for BidList
INSERT INTO BidList (account, type, bidQuantity, askQuantity, bid, ask, benchmark, bidListDate, commentary, security, status, trader, book, creationName, creationDate, revisionName, revisionDate, dealName, dealType, sourceListId, side)
VALUES ('Account1', 'Buy', 1000, 500, 100.25, 101.5, 'Benchmark1', NOW(), 'Test Commentary', 'Security1', 'Active', 'Trader1', 'Book1', 'Creator1', NOW(), 'Revisor1', NOW(), 'Deal1', 'Type1', 'Source1', 'Buy');

-- Sample Insert Data for Trade
INSERT INTO Trade (account, type, buyQuantity, sellQuantity, buyPrice, sellPrice, tradeDate, security, status, trader, benchmark, book, creationName, creationDate, revisionName, revisionDate, dealName, dealType, sourceListId, side)
VALUES ('Account2', 'Sell', 1000, 500, 99.5, 100.75, NOW(), 'Security2', 'Active', 'Trader2', 'Benchmark2', 'Book2', 'Creator2', NOW(), 'Revisor2', NOW(), 'Deal2', 'Type2', 'Source2', 'Sell');

-- Sample Insert Data for CurvePoint
INSERT INTO CurvePoint (CurveId, asOfDate, term, value, creationDate)
VALUES (1, NOW(), 5.0, 99.5, NOW());

-- Sample Insert Data for Rating
INSERT INTO Rating (moodysRating, sandPRating, fitchRating, orderNumber)
VALUES ('A', 'A+', 'A-', 1);

-- Sample Insert Data for RuleName
INSERT INTO RuleName (name, description, json, template, sqlStr, sqlPart)
VALUES ('Rule1', 'Test Rule Description', '{"rule": "value"}', 'Template1', 'SELECT * FROM table', 'WHERE condition = 1');
