import {
  listingService,
  type ProductListingDTO,
} from '@/services/listingService'
import { Button } from './ui/button'
import { Card } from './ui/card'
import { useQuery } from '@tanstack/react-query'
import { useQueryParams } from '@/lib/utils'
import { ListingPagination } from '@/components/ListingPagination'

// const products = [
//   {
//     id: 1,
//     name: 'Modern Lamp',
//     price: '$49.99',
//     description:
//       'A stylish lamp for your desk or nightstand. Crafted with premium materials.',
//     imageUrl: '/images/lamp.jpg',
//   },
//   {
//     id: 2,
//     name: 'Ergonomic Chair',
//     price: '$129.99',
//     description:
//       'Ergonomically designed chair to improve posture and reduce back pain.',
//     imageUrl: '/images/chair.jpg',
//   },
//   {
//     id: 3,
//     name: 'Wooden Desk',
//     price: '$299.00',
//     description: 'Spacious wooden desk with cable management and sturdy legs.',
//     imageUrl: '/images/desk.jpg',
//   },
//   {
//     id: 4,
//     name: 'Wireless Keyboard',
//     price: '$69.99',
//     description:
//       'Slim, quiet keyboard with Bluetooth connectivity and long battery life.',
//     imageUrl: '/images/keyboard.jpg',
//   },
// ]

const LISTING_QUERY = 'query'
const LISTING_PAGE = 'page'
const LISTING_ORDER = 'order'

export const Listings = () => {
  const queryParams = useQueryParams()
  const searchTerm = queryParams.get(LISTING_QUERY)
  const pageTerm = queryParams.get(LISTING_PAGE)
  const orderTerm = queryParams.get(LISTING_ORDER)
  const { data } = useQuery({
    queryKey: ['listings', searchTerm],
    queryFn: async (): Promise<ProductListingDTO> => {
      if (!searchTerm) {
        console.log('got no search term')
        throw Error('no search term bro')
      }
      return await listingService.getListingsByQuery(
        searchTerm,
        pageTerm,
        orderTerm,
      )
    },
    enabled: !!searchTerm,
  })
  return (
    <>
      {searchTerm && data && (
        <>
          <div className="flex flex-col gap-6 p-6">
            {data.products.map((product) => (
              <Card
                key={product.id}
                className="flex flex-row items-center gap-6 p-4"
              >
                <div className="w-32 h-32 flex-shrink-0">
                  <img
                    src="https://static1.squarespace.com/static/530cd931e4b0e49b19b254ec/t/63c6068bcdde5a79958619df/1673922187854/final+logo++copy-1+%281%29.png"
                    alt={product.name}
                    className="w-full h-full object-cover rounded-md"
                  />
                </div>

                <div className="flex-1">
                  <h3 className="text-lg font-semibold">{product.name}</h3>
                  <p className="text-sm text-muted-foreground">
                    {product.description}
                  </p>
                </div>

                <div className="flex flex-col items-end gap-2">
                  <p className="text-lg font-bold">{product.price}</p>
                  <Button className="cursor-pointer">Add to Cart</Button>
                </div>
              </Card>
            ))}
          </div>
          <ListingPagination
            pages={data.pages}
            currentPage={data.currentPage}
            query={searchTerm}
          />
        </>
      )}
    </>
  )
}
