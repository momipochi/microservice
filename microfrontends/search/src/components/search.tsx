import { useEffect, useRef, useState } from 'react'
import { Input } from './ui/input'
import { Button } from './ui/button'
import { useQuery } from '@tanstack/react-query'
import { searchService, type Product } from '@/services/searchService'

export const Search = () => {
  const [query, setQuery] = useState('')
  const [open, setOpen] = useState(false)
  const wrapperRef = useRef(null)
  const { data } = useQuery({
    queryKey: ['products', query],
    queryFn: async (): Promise<Product[]> => {
      return await searchService.getProductsByQuery(query)
    },
    enabled: query.length > 0,
    refetchOnWindowFocus: false,
  })
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        wrapperRef.current &&
        !(wrapperRef.current as HTMLElement).contains(event.target as Node)
      ) {
        setOpen(false)
      }
    }

    document.addEventListener('mousedown', handleClickOutside)
  }, [])

  const filtered = data
    ? data.filter((product: { name: string }) =>
        product.name.toLowerCase().includes(query.toLowerCase()),
      )
    : []
  return (
    <div className="relative w-full max-w-md" ref={wrapperRef}>
      <div className="flex items-center gap-2 w-full">
        <Input
          type="search"
          placeholder="Search..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => setOpen(true)}
        />
        <Button>Search</Button>
      </div>

      {open && (
        <div className="absolute top-full left-0 mt-1 w-full bg-white border rounded-md shadow z-10">
          {filtered.map((x, i) => (
            <div
              className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
              key={i}
              onClick={() => {
                console.log('Setting query: ', filtered[i])
                setQuery(filtered[i].name)
                setOpen(false)
              }}
            >
              {x.name}
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
