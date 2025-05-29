import { apiFetch } from '@/lib/api'

export interface ProductDTO {
  id: string
  name: string
  description: string
  price: number
}

export const listingService = {
  getListingsByQuery: async (query: string): Promise<ProductDTO[]> => {
    const res: ProductDTO[] = await apiFetch(`/listing?query=${query}`)
    return res
  },
}
