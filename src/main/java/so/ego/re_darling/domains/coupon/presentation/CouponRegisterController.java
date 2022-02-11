package so.ego.re_darling.domains.coupon.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import so.ego.re_darling.domains.coupon.applicateion.CouponRegisterService;
import so.ego.re_darling.domains.coupon.applicateion.dto.CouponResisterRequest;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class CouponRegisterController {

    private final CouponRegisterService couponRegisterService;

    @PostMapping("/coupon")
    public ResponseEntity<String> addCoupon(@RequestBody CouponResisterRequest couponResisterRequest){
        Long couponIndex = couponRegisterService.addCoupon(couponResisterRequest);
        return new ResponseEntity<>("Coupon Index [ "+couponIndex+" ] "+"has been issued.", HttpStatus.CREATED);
    }

}
