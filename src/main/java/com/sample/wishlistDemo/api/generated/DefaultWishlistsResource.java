
package com.sample.wishlistDemo.api.generated;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.sample.wishlistDemo.api.generated.WishlistItem;
import com.sample.wishlistDemo.api.generated.Wishlist;
import com.sample.wishlistDemo.api.generated.YaasAwareParameters;
import com.sample.wishlistDemo.WishlistService;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiWishlistsResource")
@Singleton
public class DefaultWishlistsResource implements com.sample.wishlistDemo.api.generated.WishlistsResource
{
	@javax.ws.rs.core.Context
	private javax.ws.rs.core.UriInfo uriInfo;
	
	@Autowired
    private WishlistService wishlistService;

	/* GET / */
	@Override
	public Response get(final YaasAwareParameters yaasAware)
	{
		// place some logic here
		Wishlist[] wishlists = wishlistService.getWishlists(yaasAware);
		
		return Response.ok()
			.entity(wishlists).build();
	}

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Wishlist wishlist)
	{
		// place some logic here		
		String result = wishlistService.postWishList(yaasAware, wishlist);
		
		return Response.created(uriInfo.getAbsolutePath())
			.build();
	}

	/* GET /{wishlistId} */
	@Override
	public Response getByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.ok()
			.entity(new Wishlist()).build();
	}

	/* PUT /{wishlistId} */
	@Override
	public Response putByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId, final Wishlist wishlist)
	{
		// place some logic here
		return Response.ok()
			.build();
	}

	/* DELETE /{wishlistId} */
	@Override
	public Response deleteByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.noContent()
			.build();
	}

	@Override
	public
	Response getByWishlistIdWishlistItems(
			final YaasAwareParameters yaasAware,  final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.ok()
				.entity(new java.util.ArrayList<WishlistItem>()).build();
	}

	@Override
	public
	Response postByWishlistIdWishlistItems(final YaasAwareParameters yaasAware,
			final java.lang.String wishlistId, final WishlistItem wishlistItem){
		// place some logic here
		return Response.noContent()
					.build();
	}

}
