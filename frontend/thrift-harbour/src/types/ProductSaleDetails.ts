export interface UserDetails {

    userID: number;
    firstName: string;
    lastName: string;
    email: string;
    avgBuyerRatings: number;
    avgSellerRatings: number;
    username: string;
}

export interface ImmediateSaleProductDetail {

    immediateSaleListingID: string;
    productName: string;
    productDescription: string;
    price: number;
    category: string;
    sellerEmail: string;
    active: boolean;
    approverEmail: string;
    messageFromApprover: string;
    approved: boolean;
    rejected: boolean;
    sold: boolean;
    seller: UserDetails;
    imageUrl?: string[] | undefined;
}


export interface AuctionSaleProductDetail {
    auctionSaleListingID: string,
    productName: string,
    productDescription: string,
    startingBid: number,
    highestBid: number,
    category: string;
    sellerEmail: string;
    active: boolean;
    approverEmail: string;
    messageFromApprover: string;
    approved: boolean;
    rejected: boolean;
    sold: boolean;
    seller: UserDetails;
    imageUrl?: string[] | undefined;
}