//package ru.netology.cloudstorage.security.jwt;
//
//import ru.netology.cloudstorage.model.entity.User.User;
//
//public final class JwtUserFactory {
//
//    public JwtUserFactory() {
//    }
//
//    public static JwtUser create(User user) {
//        return new JwtUser(
//                user.getId(),
//                user.getUsername(),
//                user.getPassword(),
//                user.getRole()
//        );
//    }
//
////    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
////        return userRoles.stream()
////                .map(role -> new SimpleGrantedAuthority(role.getName())
////                ).collect(Collectors.toList());
////    }
//}
