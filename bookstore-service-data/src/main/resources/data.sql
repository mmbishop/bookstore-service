insert into Author (First_Name, Middle_Name, Last_Name) values ('Kim', 'Stanley', 'Robinson');
insert into Author (First_Name, Middle_Name, Last_Name) values ('Isaac', null, 'Asimov');
insert into Author (First_Name, Middle_Name, Last_Name) values ('Edgar', 'Allan', 'Poe');

insert into Genre (Name, Pricing_Factor) values ('Science Fiction', 1.1);
insert into Genre (Name, Pricing_Factor) values ('Fiction', 1.0);

insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('Red Mars', 1, 'Spectra', 1993, '978-0553560732', 572, 12.58, 1);
insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('Green Mars', 1, 'Spectra', 1995, '978-0553572391', 624, 13.73, 1);
insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('Blue Mars', 1, 'Spectra', 1997, '978-0553573350', 784, 17.25, 1);
insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('Foundation', 2, 'Spectra', 2004, '978-0553568910', 255, 5.61, 1);
insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('Foundation and Empire', 2, 'Spectra', 2004, '978-0553293370', 320, 7.04, 1);
insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('Second Foundation', 2, 'Spectra', 2004, '978-0553293362', 304, 6.69, 1);
insert into Book (Title, Author_Id, Publisher, Publish_Year, Isbn, Number_Of_Pages, Price, Genre_Id)
    values ('The Tales of Edgar Allan Poe', 3, 'Independently published', 2019, '978-1686604515', 223, 4.46, 2);
