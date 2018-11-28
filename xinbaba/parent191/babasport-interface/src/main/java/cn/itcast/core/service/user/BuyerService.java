package cn.itcast.core.service.user;

import cn.itcast.core.bean.user.Buyer;

public interface BuyerService {

	Buyer selectBuyerByUsername(String username);

}
