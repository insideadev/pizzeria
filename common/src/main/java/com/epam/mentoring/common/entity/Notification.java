package com.epam.mentoring.common.entity;

import com.epam.mentoring.common.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long orderId;
    private OrderStatus orderStatus;
}
