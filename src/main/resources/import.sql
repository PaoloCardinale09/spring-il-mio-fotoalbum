INSERT INTO `photos` (`id`, `description`, `title`, `url`, `visible`) VALUES (NULL, 'Sun picture', 'the Sun', 'https://static.bhphotovideo.com/explora/sites/default/files/ts-space-sun-and-solar-viewing-facts-versus-fiction.jpg', b'1'), (NULL, 'the moon picture', 'the Moon', 'https://images.immediate.co.uk/production/volatile/sites/25/2019/04/The-Moon-fafa62f.jpg', b'1')


INSERT INTO `categories` (`id`, `description`, `name`) VALUES (NULL, 'galaxy pics', 'Planet'), (NULL, 'Pics of nature', 'Nature');

INSERT INTO `category_photo` (`photo_id`, `category_id`) VALUES ('1', '1'), ('2', '2');

INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`) VALUES (NULL, 'mariorossi@gmail.com', 'mario', 'rossi', '{noop}ok'), (NULL, 'paolo@gmail.com', 'paolo', 'cardinale', '{noop}ok')

INSERT INTO `roles` (`id`, `name`) VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO `users_roles` (`user_id`, `roles_id`) VALUES ('1', '1'), ('2', '2');

INSERT INTO `messages` (`id`, `email`, `message`) VALUES (NULL, 'alfio09@gmail.com', 'Ciao vorrei avere informazioni'), (NULL, 'poi@hotmail.com', 'Bellissima questa foto');
